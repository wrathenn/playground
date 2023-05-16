from essential_generators import DocumentGenerator, MarkovWordGenerator


def generate_test_records(
    count: int,
    begin: int,
    dest_filename: str,
    delimiter: str
) -> None:
    doc_generator = DocumentGenerator()
    word_generator = MarkovWordGenerator()

    with open(dest_filename, "a") as f:
        for i in range(begin, begin + count):
            descr = doc_generator.sentence()
            descr = descr.replace('\n', '')
            descr = descr.replace(delimiter, '')

            name = word_generator.gen_word()
            name = name.replace('\n', '')
            name = name.replace(delimiter, '')

            f.write(str(i + 1))
            f.write(delimiter)
            f.write(name)
            f.write(delimiter)
            f.write(descr)
            f.write('\n')
    print(f'Сгенерировано {count} записей в файл {dest_filename}')


if __name__ == '__main__':
    generate_test_records(1000, 0, 'dataset_1000.txt', ';')
