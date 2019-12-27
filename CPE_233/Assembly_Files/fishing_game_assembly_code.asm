.CSEG
.ORG 0x01 ; code starts here

.equ life = 0x05
.equ counter = 0x00
.EQU VGA_HADD = 0x90
.EQU VGA_LADD = 0x91
.EQU VGA_COLOR = 0x92

start:
sei
mov r16, 0x3c
mov R30, life
mov R12, 0x00
mov r20, counter
mov R11, 0x00
mov R6, 0x13
call draw_background
start_wait:
CMP R11, 0x5A
breq cast_wait_init
mov r15,0x00
mov r14, 0x00
brn start_wait

keypress:
IN R11, 0x44
cmp R11, 0x76
breq quit
cmp R11, 0x29
breq catch_check
retie

catch_check:
cmp r0, 0x01
breq catch_on
retie

catch_on:
mov r15, 0x01
retie

cast_wait_init:
call draw_boat
cast_wait:
mov r16, 0x3c
mov r15, 0x00
mov R20, 0x00
out R20, 0x81
cmp r14, 0x01
breq score_init
cmp R30, 0x00
breq score_init
mov R0, 0x00
cmp R11, 0x1B
breq cast_loop_init
cmp R11, 0x72;make sure to add num for down arrow here
breq cast_loop_init
brn cast_wait



cast_loop_init:
Mov R29, 0x09 ;start y pixel
Mov R28, 0x29 ;start x pixel
mov r27, 0x00

sub r30, 0x01
mov R0, 0x01
mov R20, 0x00
cast_loop_draw:
call draw_boat
cast_loop:
;cmp r20, 0x29
;breq skip_cast
;cmp r20, 0x30
;breq skip_cast
;mov r27,r20
;mov r6, 0x03
;mov r7, 0x31
;mov r8, r27
;call draw_dot
;add r27, 0x01
;mov r6, 0x13
;mov r7, 0x31
;mov r8, r27
;call draw_dot
;skip_cast:
mov r27,r20
cmp r20, r29
mov r7, r29
mov r8, 0x29
brcs continue
cmp r20, 0x3c
brcc continue
mov r7, r20
continue:
mov r6, 0xaa
call draw_dot
mov r6, 0x1a
cmp r14, 0x01
breq score_init
cmp r15, 0x01
breq catch
cmp R11, 0x1D
breq reel_loop
cmp R11, 0x75
breq reel_loop
Add r20, 0x01
cmp r20, 0xff   
breq bottom
out r20, 0x81
call delay_short
;add visual effect
brn cast_loop

bottom:
cmp r14, 0x01
breq score_init
cmp r15, 0x01
breq catch
cmp R11, 0x1D
breq reel_loop_init
cmp R11, 0x75;add up arrow
breq reel_loop_init
brn bottom

top:
cmp r14, 0x01
breq score_init
cmp r15, 0x01
breq catch
cmp R11, 0x1B
breq cast_loop_draw
cmp R11, 0x72;make sure to add num for down arrow here
breq cast_loop_draw
brn top

reel_loop_init:

reel_loop:
;cmp r20, 0x29
;breq skip_reel
;cmp r20, 0x28
;breq skip_reel
;mov r27,r20
;mov r6, 0x03
;mov r7, 0x31
;mov r8, r27
;call draw_dot
;sub r27, 0x01
;mov r6, 0x13
;mov r7, 0x31
;mov r8, r27
;call draw_dot
;skip_reel:
cmp r14, 0x01
breq score_init
cmp r15, 0x01
breq catch
cmp R11, 0x1B
breq cast_loop
cmp R11, 0x72;make sure to add num for down arrow here
breq cast_loop
sub r20, 0x01
cmp r20, 0x00
breq top
out r20, 0x81
call delay_short
brn reel_loop

delay_short:
IN R1, 0xFF
MOV R2, 0xFF
MOV R3, 0x0a
MOV R4, 0x01
LOOP: 
SUB R1, 0x01
BRNE LOOP
brn next

loop_next:
mov r1, 0xff
brn loop

next:
clc
SUB R2, 0x01
breq follow
brcc loop_next
BRN next


follow_next:
mov r2, 0xff
brn next

follow:
clc
SUB R3, 0x01
Breq returner
BRCC follow_next
BRN follow

new:
SUB R4, 0x01
MOV r3, 0xFF
BRCS returner
brn follow
returner:
ret


score_init:
mov r14, 0x00
mov R11, 0x00
mov r16, 0x3c
score:
mov r6, 0xf1
call draw_score
out r12, 0x81
cmp R11, 0x5A
breq start
brn score


   ;no more lives
;total score display 
;ask user if they want to replay: press enter.  quit: press esc


catch: 
;when the space button is pressed, the user attempted to catch
;types of catch
.equ seaweed = 0x05
.equ rare = 0x64
.equ common = 0x19
mov r15, 0x00
cmp R20, 0x3a
brcs c_seaweed
cmp R20, 0x97
brcs c_common
brn c_rare


c_seaweed:
add r12, 0x01
call draw_seaweed
brn catch_loop


c_common:
add r12, 0x05
call draw_common
brn catch_loop

c_rare:
add r12, 0x0a
call draw_rare
brn catch_loop

catch_loop:
cmp r11, 0x5A
breq cast_wait_init
brn catch_loop

quit:
mov r14, 0x01
retie

draw_common:
mov r6, 0x13
mov r16, 0x3c
call draw_background
mov r6, 0x03
mov r8, 0x04
mov r7, 0x09
mov r9, 0x0a
call draw_triangle
mov r7, 0x1d
mov r8, 0x04
mov r9, 0x09
call draw_inv_triangle
mov r7, 0x11
mov r8, 0x0c
mov r22, 0x14
mov r9, 0x45
call draw_rectangle
mov r7, 0x0d
mov r8, 0x11
mov r9, 0x45
mov r22, 0x18
call draw_rectangle
mov r8, 0x29
mov r7, 0x08
mov r9, 0x05
call draw_triangle
mov r8, 0x31
mov r7, 0x1d
mov r9, 0x04
call draw_inv_triangle
mov r8, 0x46
mov r7, 0x0d
mov r9, 0x06
call draw_triangle
mov r8, 0x46
mov r7, 0x18
mov r9, 0x05
call draw_inv_triangle
ret

draw_score:
mov r6, 0xaa
mov r16, 0x3c
call draw_background
ret

draw_boat: 

mov r6, 0x03
mov r16, 0x3c
call draw_background
mov r6, 0x13
mov r16, 0x27
call draw_background
mov r6, 0x1a
mov r16, 0x0a
call draw_background
mov r6, 0x8c ;brown
Mov r7, 0x09
mov r8,0x14 ;top left corner
mov r9,0x28 ;top right corner 
call draw_horizontal_line
Mov r7, 0x0a
mov r8,0x19  ;bottom left 
mov r9,0x23  ;bottom right
call draw_horizontal_line

ret

draw_rare:
mov r6, 0x13
mov r16, 0x3c
call draw_background
mov r6, 0x03
mov r8, 0x04
mov r7, 0x09
mov r9, 0x0b
call draw_triangle
mov r7, 0x1f
mov r8, 0x04
mov r9, 0x0a
call draw_inv_triangle
mov r8, 0x0f
mov r7, 0x09
mov r9, 0x0b
call draw_triangle
mov r7, 0x1f
mov r8, 0x0f
mov r9, 0x0a
call draw_inv_triangle
mov r8, 0x18
mov r7, 0x09
mov r9, 0x0b
call draw_triangle
mov r7, 0x1f
mov r8, 0x18
mov r9, 0x0a
call draw_inv_triangle
mov r8, 0x22
mov r7, 0x09
mov r9, 0x0b
call draw_triangle
mov r7, 0x1f
mov r8, 0x22
mov r9, 0x0a
call draw_inv_triangle
mov r8, 0x2b
mov r7, 0x09
mov r9, 0x0b
call draw_triangle
mov r7, 0x1f
mov r8, 0x2b
mov r9, 0x0a
call draw_inv_triangle
mov r8, 0x34
mov r7, 0x09
mov r9, 0x0b
call draw_triangle
mov r7, 0x1f
mov r8, 0x34
mov r9, 0x0a
call draw_inv_triangle
mov r7, 0x14
mov r8, 0x37
mov r6, 0x00
call draw_dot
ret




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

returnerr:
ret
draw_rectangle:
mov r24, r8
mov r23, r9
mov r21, r7
draw_l_r:
CALL  draw_horizontal_line
cmp r7, r22
breq returnerr
mov r9, r23
mov r8, r24
add r21, 0x01
mov r7, r21
brn draw_l_r

draw_seaweed:
mov r6, 0x13
mov r16, 0x3c
call draw_background
;upper part
mov r6, 0x0a  ;seaweed green 
mov r8, 0x12  ;x1 coord
call draw_seaweed_up_leaf
mov r8, 0x20 ;second leaf: x1 coord
call draw_seaweed_up_leaf
mov r8, 0x2e  ;third leaf: x2 coord
call draw_seaweed_up_leaf
mov r8, 0x3d  ;forth leaf: x2 coord
call draw_seaweed_up_leaf
;branch 
Mov r7, 0x10  ;y coord
Mov r8, 0x0b ;x1 coord: first layer branch 
Mov r9, 0x4a ;x2 coord 
call draw_horizontal_line
mov r7, 0x11  ;y coord
mov r8, 0x0e ;x1 coord: second layer branch 
call draw_horizontal_line
mov r7, 0x12 ;y coord
mov r8, 0x06 ;x1 coord: third layer branch
mov r9, 0x11 ;x2 coord
call draw_horizontal_line
;below part
mov r8, 0x16 ;first lower leaf: x1 coord 
call draw_seaweed_low_leaf
mov r8, 0x23 ;second lower leaf: x1 coord
call draw_seaweed_low_leaf
mov r8, 0x31 ;third lower leaf: x1 coord
call draw_seaweed_low_leaf
mov r8, 0x3D ;forth lower leaf: x1 coord
call draw_seaweed_low_leaf
mov r8, 0x49 ;fifth lower leaf: x1 coord
call draw_seaweed_low_leaf
ret

draw_seaweed_up_leaf:
mov r9,r8 
add r9, 0x03 ;x2 coord 
mov r7, 0x06  ;y1 coord  ;constant 
mov r22,0x08  ;y2 coord  ;constant
call draw_rectangle 
add r9, 0x01  
mov r8, r9  ;new x1 coord
add r9, 0x04 ;new x2 coord
add r22, 0x05 ;new y2 coord
call draw_rectangle 
add r9, 0x01 
mov r8, r9 ;new x1 coord
add r9, 0x03 ;new x2 coord
add r7, 0x02 ;new y1 coord
add r22, 0x04 ;new y2 coord
call draw_rectangle
add r9, 0x01 
mov r8, r9 ;new x1 coord  
add r9, 0x02 ;new x2 coord
add r7, 0x02 ;new y1 coord
add r22, 0x04 ;new y2 coord
call draw_rectangle
mov r6, 0x8c ;brown node
add r9, 0x02 ;new x2 coord
add r22, 0x01 
mov r7, r22  ;y coord
call draw_horizontal_line
mov r6, 0x0a ;seaweed green 
mov r8, r9 ;x coord
sub r7, 0x01 
mov	r9, r7  ;ending y coord
sub r7, 0x02 ;starting y coord
call draw_vertical_line	
RET

draw_seaweed_low_leaf:
ret
mov r6, 0x8c ;brown node
mov r7, 0x12  ;y coord  ;constant
mov r9, r8  
mov r9, 0x01 ;x2 coord
call draw_horizontal_line
mov r6, 0x0a ;seaweed green lower leaf
sub r8, 0x03 ;new x1 coord
add r7, 0x01 ;y1 coord
mov r22, r7 
add r22, 0x01 ;y2 coord
call draw_rectangle 
sub r8, 0x02 ;new x1 coord
sub r9, 0x01 ;new x2 coord
add r22, 0x01 
mov r7, r22 ;new y1 coord
add r22,0x02 ;new y2 coord
call draw_rectangle 
sub r8, 0x02 ;new x1 coord
sub r9, 0x02 ;new x2 coord
add r22,0x01 
mov r7, r22 ;new y1 coord
add r22,0x02 ;new y2 coord
call draw_rectangle
sub r9, 0x04 ;new x2 coord
add r22, 0x01 
mov r7, r22 ;new y coord
call draw_horizontal_line 
RET


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




.org 0x3ff
brn keypress



;watchout of keypress moving multiple loops at a time









