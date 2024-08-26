declare i32 @printf(ptr noundef, ...)
declare i32 @scanf(ptr noundef, ...)


define i32 @b() {
  %1 = call i32 (i32) @a(i32 123)
  ret i32 %1
}

define i32 @a(i32 %t) {
  ret i32 %t
}

define i32 @main() #0 {
entry:
  %0 = call i32 @b()
  %call = call i32 (ptr, ...) @printf(ptr noundef @str.0, i32 %0)
  ret i32 0
}
@str.0 = global [8 x i8] c"[ %d ] \00"
