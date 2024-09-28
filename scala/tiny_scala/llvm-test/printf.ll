declare ptr @malloc(i64)
declare i32 @printf(ptr noundef, ...)
@Test.a = global ptr undef
@Test.b = global ptr undef
@str.0 = global [4 x i8] c"%d\0A\00"

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

; start of init for Test.b
%v_6 = alloca i32
store i32 3, ptr %v_6
%v_6.value = load i32, ptr %v_6
%v_7 = alloca i32
store i32 2, ptr %v_7
%v_7.value = load i32, ptr %v_7
%v_8 = alloca i32
store i32 6, ptr %v_8
%v_8.value = load i32, ptr %v_8
%v_9 = mul i32 %v_7.value, %v_8.value
%v_10 = sub i32 %v_6.value, %v_9
%v_11 = alloca i32
store i32 8, ptr %v_11
%v_11.value = load i32, ptr %v_11
%v_12 = alloca i32
store i32 2, ptr %v_12
%v_12.value = load i32, ptr %v_12
%v_13 = sdiv i32 %v_11.value, %v_12.value
%v_14 = add i32 %v_10, %v_13
store i32 %v_14, ptr @Test.b ; init done

%v_16 = alloca i32
store i32 3, ptr %v_16
%v_16.value = load i32, ptr %v_16
%v_17 = alloca i32
store i32 2, ptr %v_17
%v_17.value = load i32, ptr %v_17
%v_18 = add i32 %v_16.value, %v_17.value
%v_19 = alloca i32
store i32 1, ptr %v_19
%v_19.value = load i32, ptr %v_19
%v_20 = sub i32 %v_18, %v_19.value
%v_21 = call i32 (ptr, ...) @printf(ptr @str.0, i32 %v_20)

; --- MAIN CODE: ---
ret i32 0
}