all: out/installer out/main

out/installer:
	cd installer && cargo build && cp target/debug/installer ../out/installer

out/main:
	cd main && cargo build && cp target/debug/main ../out/main

clean:
	rm out/*

install:
	cd out && ./installer -i main -o installed && ./installed
