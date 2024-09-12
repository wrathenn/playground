declare ptr @malloc(i64)
declare i32 @printf(ptr noundef, ...)
; --- GLOBAL CODE: ---
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
@A.a = global ptr undef

; --- LOCAL CODE: ---
define i32 @Test.foo (i32 %a) {
  %v_0.value = load i32, ptr @Test.a
  %v_1 = alloca i32
  store i32 3, ptr %v_1
  %v_1.value = load i32, ptr %v_1
  %v_2 = add i32 %v_0.value, %v_1.value
  ret i32 %v_2
}

define i32 @main() #0 {
; --- INIT CODE: ---
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

; start of init for A.a
%v_8 = alloca i32
store i32 1, ptr %v_8
%v_8.value = load i32, ptr %v_8
%v_9 = alloca double
store double 2.0, ptr %v_9
%v_9.value = load double, ptr %v_9
%v_10.size = getelementptr %A, ptr null, i32 1
%v_10.size.i = ptrtoint ptr %v_10.size to i64
%v_10 = call ptr @malloc(i64 %v_10.size.i)
%v_10.f0.ptr = getelementptr %A, ptr %v_10, i32 0, i32 0
store i32 %v_8.value, ptr %v_10.f0.ptr
%v_10.f1.ptr = getelementptr %A, ptr %v_10, i32 0, i32 1
store double %v_9.value, ptr %v_10.f1.ptr
store ptr %v_10, ptr @A.a ; init done


; --- MAIN CODE: ---
ret i32 0
}