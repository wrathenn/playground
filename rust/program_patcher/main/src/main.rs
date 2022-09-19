use std::fs::File;
use std::io::Read;
use sha2::{Digest, Sha256};

const HASH_KEY_SPACE: [u8; 32] = ['?' as u8; 32];

fn read_machine_id() -> Result<Vec<u8>, &'static str> {
    let mut file = match File::open("/etc/machine-id") {
        Ok(f) => f,
        Err(_) => return Err("Error open machine-id")
    };
    let mut res= Vec::with_capacity(32);
    file.read_to_end(&mut res).unwrap();
    Ok(res)
}

fn hash_key(data: impl AsRef<[u8]>) -> Vec<u8> {
    let mut hasher = Sha256::new();
    hasher.update(data);
    hasher.finalize().to_vec()
}

fn check_key() -> bool {
    let machine_id = read_machine_id().unwrap();
    let machine_id = hash_key(machine_id);
    machine_id == HASH_KEY_SPACE
}

fn main() {
    if !check_key() {
        println!("You can't run program on this computer");
        return
    }
    println!("You have access to program!");
}
