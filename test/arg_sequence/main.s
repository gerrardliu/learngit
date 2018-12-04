	.file	"main.c"
	.section	.rodata
.LC0:
	.string	"%d %d %d %d\n"
	.text
	.globl	main
	.type	main, @function
main:
.LFB0:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	subq	$16, %rsp
	movl	$10, -4(%rbp)
	movl	-4(%rbp), %edx
	leal	1(%rdx), %eax
	movl	%eax, -4(%rbp)
	addl	$1, -4(%rbp)
	movl	-4(%rbp), %eax
	leal	1(%rax), %ecx
	movl	%ecx, -4(%rbp)
	movl	-4(%rbp), %ecx
	movl	-4(%rbp), %esi
	movl	%edx, %r8d
	movl	%esi, %edx
	movl	%eax, %esi
	movl	$.LC0, %edi
	movl	$0, %eax
	call	printf
	movl	$0, %eax
	leave
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	main, .-main
	.ident	"GCC: (GNU) 4.8.5 20150623 (Red Hat 4.8.5-28)"
	.section	.note.GNU-stack,"",@progbits
