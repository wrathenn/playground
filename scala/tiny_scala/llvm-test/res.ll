declare ptr @malloc(i64)
declare i32 @printf(ptr noundef, ...)

@str.0 = global [9 x i8] c"[ %d ] \0A\00"

%A = type {
  i32, ; v1
  double ; v2
}

%B = type {
  i64, ; a
  ptr ; aa
}

@Test.a = global ptr undef
@Test.test_value = global ptr undef

define i32 @main() #0 {
entry:
; start of init for Test.a
%v_0 = alloca i32
store i32 727, ptr %v_0
%v_0.value = load i32, ptr %v_0
store i32 %v_0.value, ptr @Test.a ; init done

; start of init for Test.test_value
%v_1.value = load i32, ptr @Test.a
%v_2 = alloca i32
store i32 3, ptr %v_2
%v_2.value = load i32, ptr %v_2
%v_3 = alloca i32
store i32 4, ptr %v_3
%v_3.value = load i32, ptr %v_3
%v_4 = mul i32 %v_2.value, %v_3.value
%v_5 = alloca i32
store i32 2, ptr %v_5
%v_5.value = load i32, ptr %v_5
%v_6 = sdiv i32 %v_4, %v_5.value
%v_7 = add i32 %v_1.value, %v_6
store i32 %v_7, ptr @Test.test_value ; init done

%i32_format_ptr = getelementptr [9 x i8], [9 x i8]* @str.0, i32 0, i32 0
%print_v1 = call i32 (i8*, ...) @printf(i8* %i32_format_ptr, i32 %v_7)

ret i32 0
}
