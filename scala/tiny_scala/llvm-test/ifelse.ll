declare ptr @malloc(i64)
declare i32 @printf(ptr noundef, ...)
; --- GLOBAL CODE: ---
@Test.a = global ptr undef

; --- LOCAL CODE: ---

define i32 @main() #0 {
; --- INIT CODE: ---
entry:
; start of init for Test.a
%v_0 = alloca i32
store i32 1, ptr %v_0
%v_0.value = load i32, ptr %v_0
%v_1 = alloca i32
store i32 2, ptr %v_1
%v_1.value = load i32, ptr %v_1
%v_2 = icmp eq i32 %v_0.value, %v_1.value
br i1 %v_2, label %v_2.then_branch, label %v_2.else_branch
v_2.then_branch:
%v_3 = alloca i32
store i32 2, ptr %v_3
%v_3.value = load i32, ptr %v_3
br label %v_2.end_branch
v_2.else_branch:
%v_4 = alloca i32
store i32 3, ptr %v_4
%v_4.value = load i32, ptr %v_4
br label %v_2.end_branch
v_2.end_branch:
%v_5 = phi i32 [%v_3.value, %v_2.then_branch], [%v_4.value, %v_2.else_branch]
store i32 %v_5, ptr @Test.a ; init done


; --- MAIN CODE: ---
ret i32 0
}