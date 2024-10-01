; --- GLOBAL CODE: ---
declare ptr @malloc(i64)
declare i32 @printf(ptr noundef, ...)

%Test = type {
  i32 ; type tag
  i32, ; a
  double ; b
}

@Test.c = global ptr undef

; --- LOCAL CODE: ---

define i32 @main() #0 {
; --- INIT CODE: ---
entry:
; start of init for Test.c
%v_0 = alloca i32
store i32 1, ptr %v_0
%v_0.value = load i32, ptr %v_0
%v_1 = alloca double
store double 2.0, ptr %v_1
%v_1.value = load double, ptr %v_1
%v_2.size = getelementptr %Test, ptr null, i32 1
%v_2.size.i = ptrtoint ptr %v_2.size to i64
%v_2 = call ptr @malloc(i64 %v_2.size.i)
%v_2.f0.ptr = getelementptr %Test, ptr %v_2, i32 0, i32 0
store i32 %v_0.value, ptr %v_2.f0.ptr
%v_2.f1.ptr = getelementptr %Test, ptr %v_2, i32 0, i32 1
store double %v_1.value, ptr %v_2.f1.ptr
store ptr %v_2, ptr @Test.c ; init done


; --- MAIN CODE: ---
ret i32 0
}