declare ptr @malloc(i64)
declare i32 @printf(ptr noundef, ...)

@str.0 = global [9 x i8] c"[ %d ] \0A\00"
@str.1 = global [10 x i8] c"[ %lf ] \0A\00"

%B = type {
  ptr      ; a
}

%A = type {
  i32,    ; v1
  double  ; v2
}

define i32 @MyFunctions.asd() {
  ret i32 3
}

define i32 @main() #0 {
 entry:
  %A_size = getelementptr %A, ptr null, i32 1
  %A_size_i = ptrtoint ptr %A_size to i64
  %A_ptr = call ptr @malloc(i64 %A_size_i)

  %A_v1_ptr = getelementptr %A, ptr %A_ptr, i32 0, i32 0
  store i32 1, ptr %A_v1_ptr
  %A_v2_ptr = getelementptr %A, ptr %A_ptr, i32 0, i32 1
  store double 3.0, ptr %A_v2_ptr

  %B_size = getelementptr %B, ptr null, i32 0
  %B_size_i = ptrtoint ptr %B_size to i64
  %B_ptr = call ptr @malloc(i64 %B_size_i)

  %B_a_ptr = getelementptr %B, ptr %B_ptr, i32 0, i32 0
  store ptr %A_ptr, ptr %B_a_ptr

  %asd_call = call i32 @MyFunctions.asd()

  %B_a_load_ptr = getelementptr %B, ptr %B_ptr, i32 0, i32 0
  %B_a_loaded = load ptr, ptr %B_a_load_ptr

  %A_v1_load_ptr = getelementptr %A, ptr %B_a_loaded, i32 0, i32 0
  %A_v1_loaded = load i32, ptr %A_v1_load_ptr
  %i32_format_ptr = getelementptr [9 x i8], ptr @str.0, i32 0, i32 0
  %print_v1 = call i32 (ptr, ...) @printf(ptr %i32_format_ptr, i32 %A_v1_loaded)

  %A_v2_load_ptr = getelementptr %A, ptr %B_a_loaded, i32 0, i32 1
  %A_v2_loaded = load double, ptr %A_v2_load_ptr
  %double_format_ptr = getelementptr [10 x i8], ptr @str.1, i32 0, i32 0
  %print_v2 = call i32 (ptr, ...) @printf(ptr %double_format_ptr, double %A_v2_loaded)

  ret i32 %asd_call
}
