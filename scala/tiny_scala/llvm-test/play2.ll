declare i8* @malloc(i64)
declare i32 @printf(i8* noundef, ...)

@str.0 = global [9 x i8] c"[ %d ] \0A\00"
@str.1 = global [10 x i8] c"[ %lf ] \0A\00"

%B = type {
  %A*      ; a
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
  %A_size = getelementptr %A, %A* null, i32 1
  %A_size_i = ptrtoint %A* %A_size to i64
  %A_alloced = call i8* @malloc(i64 %A_size_i)
  %A_ptr = bitcast i8* %A_alloced to %A*
  %A_v1_ptr = getelementptr %A, %A* %A_ptr, i32 0, i32 0
  store i32 1, i32* %A_v1_ptr
  %A_v2_ptr = getelementptr %A, %A* %A_ptr, i32 0, i32 1
  store double 3.0, double* %A_v2_ptr

  %B_size = getelementptr %B, %B* null, i32 0
  %B_size_i = ptrtoint %B* %B_size to i64
  %B_alloced = call i8* @malloc(i64 %B_size_i)
  %B_ptr = bitcast i8* %B_alloced to %B*
  %B_a_ptr = getelementptr %B, %B* %B_ptr, i32 0, i32 0
  store %A* %A_ptr, %A** %B_a_ptr

  %asd_call = call i32 @MyFunctions.asd()

  %B_a_load_ptr = getelementptr %B, %B* %B_ptr, i32 0, i32 0
  %B_a_loaded = load %A*, %A** %B_a_load_ptr

  %A_v1_load_ptr = getelementptr %A, %A* %B_a_loaded, i32 0, i32 0
  %A_v1_loaded = load i32, i32* %A_v1_load_ptr
  %i32_format_ptr = getelementptr [9 x i8], [9 x i8]* @str.0, i32 0, i32 0
  %print_v1 = call i32 (i8*, ...) @printf(i8* %i32_format_ptr, i32 %A_v1_loaded)

  %A_v2_load_ptr = getelementptr %A, %A* %B_a_loaded, i32 0, i32 1
  %A_v2_loaded = load double, double* %A_v2_load_ptr
  %double_format_ptr = getelementptr [10 x i8], [10 x i8]* @str.1, i32 0, i32 0
  %print_v2 = call i32 (i8*, ...) @printf(i8* %double_format_ptr, double %A_v2_loaded)

  ret i32 %asd_call
}
