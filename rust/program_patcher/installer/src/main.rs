use std::fs::File;
use std::io::{BufReader, BufWriter, Read, Write};
use std::os::unix::fs::PermissionsExt;
use clap::Parser;
use sha2::{Digest, Sha256};

#[derive(Parser, Debug)]
#[clap(author, version, about)]
struct Args {
    #[clap(short, long, value_parser)]
    input: String,
    #[clap(short, long, value_parser, default_value = "patched")]
    output: String,
}

fn read_machine_id() -> Result<Vec<u8>, &'static str> {
    let mut file = match File::open("/etc/machine-id") {
        Ok(f) => f,
        Err(_) => return Err("Error open machine-id")
    };
    let mut res= Vec::with_capacity(32);
    file.read_to_end(&mut res).unwrap();
    Ok(res)
}

fn open_files(input_filename: &str, output_filename: &str) ->
    Result<(BufReader<File>, BufWriter<File>), &'static str> {
    let input = match File::open(input_filename) {
        Ok(file) => file,
        Err(_) => return Err("Error: can't open file")
    };
    let reader = BufReader::new(input);

    let output = match File::create(output_filename) {
        Ok(file) => file,
        Err(_) => return Err("Error: can't create file")
    };
    let mut permissions = output.metadata().unwrap().permissions();
    permissions.set_mode(0o775);
    output.set_permissions(permissions).unwrap();
    let writer = BufWriter::new(output);
    Ok((reader, writer))
}

fn create_patched(mut reader: BufReader<File>, mut writer: BufWriter<File>) ->
    Result<(), &'static str> {
    let place_for_hashed_key = ['?' as u8; 32];
    let machine_id = match read_machine_id() {
        Ok(id) => id,
        Err(e) => return Err(e)
    };
    let mut hasher = Sha256::new();
    hasher.update(machine_id);
    let hashed_machine_id = hasher.finalize();

    let mut patched_file_vec = Vec::with_capacity(2048);
    reader.read_to_end(&mut patched_file_vec).unwrap();

    let mut w_cnt = usize::MAX;
    for (i, slice) in patched_file_vec.windows(place_for_hashed_key.len()).enumerate() {
        if slice == place_for_hashed_key {
            w_cnt = i;
            break;
        }
    }
    if w_cnt == usize::MAX {
        return Err("Place for hash key not found");
    }
    let (_, mut hash_place_start) = patched_file_vec.split_at_mut(w_cnt);
    hash_place_start.write(&hashed_machine_id).unwrap();

    writer.write(&patched_file_vec).unwrap();
    Ok(())
}

fn main() {
    let args: Args = Args::parse();
    let (program_reader, patched_writer) = open_files(&args.input, &args.output).unwrap();
    create_patched(program_reader, patched_writer).unwrap();
}
