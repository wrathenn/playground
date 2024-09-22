declare ptr @malloc(i64)
declare i32 @printf(ptr noundef, ...)
; --- GLOBAL CODE: ---
@Test.a = global ptr undef
@str.0 = global [9 x i8] c"[ %d ] \0A\00"

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

br label %loop.86541a89.condition
loop.86541a89.condition:
%v_6 = alloca i32
store i32 1, ptr %v_6
%v_6.value = load i32, ptr %v_6
%v_7 = alloca i32
store i32 1, ptr %v_7
%v_7.value = load i32, ptr %v_7
%v_8 = icmp eq i32 %v_6.value, %v_7.value
br i1 %v_8, label %loop.86541a89.block, label %loop.86541a89.end
loop.86541a89.block:
%i32_format_ptr = getelementptr [9 x i8], ptr @str.0, i32 0, i32 0
%print_v1 = call i32 (ptr, ...) @printf(ptr %i32_format_ptr, i32 727)
%d = alloca i32
%v_9 = alloca i32
store i32 3, ptr %v_9
%v_9.value = load i32, ptr %v_9
store i32 %v_9.value, ptr %d
%c = alloca i32
%v_10.value = load i32, ptr %d
%v_11 = alloca i32
store i32 4, ptr %v_11
%v_11.value = load i32, ptr %v_11
%v_12 = add i32 %v_10.value, %v_11.value
store i32 %v_12, ptr %c
br label %loop.86541a89.condition
loop.86541a89.end:

; --- MAIN CODE: ---
ret i32 0
}