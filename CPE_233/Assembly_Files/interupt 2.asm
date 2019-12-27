;------------------------------------------------
;--- Programmers: Chris Wright, Yu Asai	        |
;--- Date: 2.27.19			                  	|
;--- V.1				                       	|
;---					                      	|
;---A simple interupt funtion that uses xor     |
;---to switch on and off leds                   |     
;------------------------------------------------
;--- INITIALIZATION
;------------------------------------------------
.CSEG
.ORG	0x10

SEI 

Main_loop: 
Mov R0, 0xFF
out R0, 0x40
BRN Main_loop



int_loop:
MOV R0, 0xAA
out R0, 0x40
brn int_loop
retid






.ORG 0x3ff
BRN int_loop

