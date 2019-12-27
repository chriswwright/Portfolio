.cseg
.org 0x01

.EQU VGA_HADD = 0x90
.EQU VGA_LADD = 0x91
.EQU VGA_COLOR = 0x92

cmp r0, 0xff
mov r6, 0x0a
mov r7, 0x21
mov r8, 0x21
mov r22, 0x24
mov r9, 0x31
call draw_rectangle
mov r16, 0x3c
call draw_background
mov r7, 0x03
mov r6, 0x00
mov r8, 0x03
mov r9, 0x06
call draw_triangle

mov r7, 0x018
mov r6, 0x00
mov r8, 0x018
mov r9, 0x06
call draw_inv_triangle

mov r7, 0x21
mov r8, 0x21
mov r22, 0x24
mov r9, 0x31
call draw_rectangle
wait:
brn wait

draw_horizontal_line:
        ADD r9,0x01          ; go from r8 to r15 inclusive
		cmp r8,r9
		brcc draw_inv_hor
		
draw_horiz1:
        CALL   draw_dot         ; 
        ADD    r8,0x01
        CMP    r8,r9
        BRNE   draw_horiz1
        RET
		
draw_inv_hor:
         call draw_dot
		 Sub r7, 0x01 
		 cmp r7,r9
		 brne draw_inv_hor
		 ret
;--------------------------------------------------------------------


;---------------------------------------------------------------------
;-  Subroutine: draw_vertical_line
;-
;-  Draws a horizontal line from (r8,r7) to (r8,r9) using color in r6
;-
;-  Parameters:
;-   r8  = x-coordinate
;-   r7  = starting y-coordinate
;-   r9  = ending y-coordinate
;-   r6  = color used for line
;- 
;- Tweaked registers: r7,r9
;--------------------------------------------------------------------
draw_vertical_line:

         ADD    r9,0x01
		 cmp r7, r9
		 brcc draw_inv_vertl
		 
draw_vert1:          
         CALL   draw_dot
         ADD    r7,0x01
         CMP    r7,R9
         BRNE   draw_vert1
         RET
		 
draw_inv_vertl:
         call draw_dot
		 Sub r7, 0x01
		 cmp r7,r9
		 brne draw_inv_vertl
		 ret
		 
;--------------------------------------------------------------------

;---------------------------------------------------------------------
;-  Subroutine: draw_background
;-
;-  Fills the 30x40 grid with one color using successive calls to 
;-  draw_horizontal_line subroutine. 
;- 
;-  Tweaked registers: r13,r7,r8,r9
;----------------------------------------------------------------------
draw_background: 
         MOV   r13,0x00                 ; r13 keeps track of rows
repeat:  MOV   r7,r13                   ; load current row count 
         MOV   r8,0x00                  ; restart x coordinates
         MOV   r9,0x4F 					; set to total number of columns
 
         CALL  draw_horizontal_line
         ADD   r13,0x01                 ; increment row count
         CMP   r13,r16                 ; see if more rows to draw
         BRNE  repeat                    ; branch to draw more rows
         RET
;---------------------------------------------------------------------

draw_triangle:
mov r21, r8
mov r22, r8
mov r23, r7
add r21, r9
mov r9, r8
draw_t_l:
CALL  draw_horizontal_line
mov r8, r22
add r23, 0x01
mov r7, r23
cmp r9, r21
brne draw_t_l
CALL  draw_horizontal_line
ret

draw_inv_triangle:
mov r21, r8
add r21, r9
mov r22, r8
mov r23, r7
mov r9, r8
draw_t_i_l:
CALL  draw_horizontal_line
mov r8, r22
sub r23, 0x01
mov r7, r23
cmp r9, r21
brne draw_t_i_l
CALL  draw_horizontal_line
ret

returner:
ret
draw_rectangle:
mov r24, r8
mov r23, r9
mov r21, r7
draw_l_r:
CALL  draw_horizontal_line
cmp r7, r22
breq returner
mov r9, r23
mov r8, r24
add r21, 0x01
mov r7, r21
brn draw_l_r


;---------------------------------------------------------------------
;- Subrountine: draw_dot
;- 
;- This subroutine draws a dot on the display the given coordinates: 
;- 
;- (X,Y) = (r8,r7)  with a color stored in r6  
;- 
;- Tweaked registers: r4,r5
;---------------------------------------------------------------------
draw_dot: 
           MOV   r4,r7         ; copy Y coordinate
           MOV   r5,r8         ; copy X coordinate

           AND   r5,0x7F       ; make sure top 1 bits cleared
           AND   r4,0x3F       ; make sure top 2 bits cleared
           LSR   r4             ; need to get the bottom bit of r4 into sA
           BRCS  dd_add80

dd_out:    OUT   r5,VGA_LADD   ; write bot 8 address bits to register
           OUT   r4,VGA_HADD   ; write top 5 address bits to register
           OUT   r6,VGA_COLOR  ; write color data to frame buffer
           RET           

dd_add80:  OR    r5,0x80       ; set bit if needed
           BRN   dd_out
; --------------------------------------------------------------------

