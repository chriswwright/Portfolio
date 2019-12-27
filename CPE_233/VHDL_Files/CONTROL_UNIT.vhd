----------------------------------------------------------------------------------
-- Engineer: Asai_Wright
-- Create Date: 02/08/2019 12:16:44 AM
-- Module Name: CONTROL_UNIT - Behavioral
--
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity CONTROL_UNIT is
  Port (C_FLAG : in STD_LOGIC;
        Z_FLAG : in STD_LOGIC;
        INT_IN : in STD_LOGIC;
        RESET : in STD_LOGIC;
        OPCODE_HI_5 : in STD_LOGIC_VECTOR(4 downto 0);
        OPCODE_LO_2 : in STD_LOGIC_VECTOR(1 downto 0);
        CLK : in STD_LOGIC;
        I_SET, I_CLR : out STD_LOGIC;
        PC_LD, PC_INC : out STD_LOGIC;
        PC_MUX_SEL, RF_WR_SEL, SCR_ADDR_SEL : out STD_LOGIC_VECTOR (1 downto 0);
        ALU_OPY_SEL, RF_WR : out STD_LOGIC;
        ALU_SEL : out STD_LOGIC_VECTOR (3 downto 0);
        SP_LD, SP_INCR, SP_DECR : out STD_LOGIC;
        SCR_WE, SCR_DATA_SEL : out STD_LOGIC;
        FLG_C_SET, FLG_C_CLR, FLG_C_LD, FLG_Z_LD, FLG_LD_SEL, FLG_SHAD_LD : out STD_LOGIC; 
        RST_OUT, IO_STRB : out STD_LOGIC
        );
end CONTROL_UNIT;

architecture Behavioral of CONTROL_UNIT is
    type state_type is (RST, F, E, INT);
    signal PS, NS : state_type;
    signal OP_CODE : std_logic_vector (6 downto 0);
    
begin
    sync_proc: process(CLK,NS,RESET)
    begin 
        if (RESET = '1') then PS <= RST;
        elsif (rising_edge(CLK)) then PS <= NS;
        end if;
    end process sync_proc;
    OP_CODE <= OPCODE_HI_5 & OPCODE_LO_2;
executer: process(PS, OP_CODE, INT_IN, C_FLAG, Z_FLAG)
        begin
        --- initilaize all the outputs to 0 to avoid latches
        I_SET <= '0';
        I_CLR <= '0';
        PC_LD <= '0';
        PC_INC <= '0';
        PC_MUX_SEL <= "00";
        RF_WR_SEL <= "00";
        SCR_ADDR_SEL <= "00";
        ALU_OPY_SEL <= '0';
        SCR_DATA_SEL <= '0';
        ALU_SEL <= "0000";
        RF_WR <= '0';
        SP_LD <= '0';
        SP_INCR <= '0';
        SP_DECR <= '0';
        SCR_WE <= '0';
        FLG_C_SET <= '0';
        FLG_C_CLR <= '0';
        FLG_C_LD <= '0';
        FLG_Z_LD <= '0';
        FLG_LD_SEL <= '0';
        FLG_SHAD_LD <= '0';
        IO_STRB <= '0';
        RST_OUT <= '0';
        case PS is
            when INT =>
               FLG_SHAD_LD <= '1';
               PC_LD <= '1';
               PC_MUX_SEL <= "10";
               SCR_DATA_SEL <= '1'; 
               SCR_WE <= '1';
               SCR_ADDR_SEL <= "11";
               SP_DECR <= '1';              
               
               NS <= F;
            when RST =>
               RST_OUT <= '1';
               NS <= F;
            when F =>
            PC_INC <= '1';
            NS <= E;
            ---Reg/Reg---
            When E =>
            if INT_IN = '1' then
            NS <= INT;
            else 
            NS <= F;
            end if;
            case OP_CODE is         
            --LOGICAL--    
            when "0000000" =>         --AND
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '0';
                ALU_SEL <= "0101";
                FLG_Z_LD <= '1';
                FLG_C_CLR <= '1';
            when "0000001" =>         --OR
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '0';
                ALU_SEL <= "0110";
                FLG_Z_LD <= '1';
                FLG_C_CLR <= '1';
                
            when "0000010" =>         --EXOR 
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '0';
                ALU_SEL <= "0111";
                FLG_Z_LD <= '1';
                FLG_C_CLR <= '1';
            when "0000011" =>         --TEST
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '0';
                ALU_SEL <= "1000"; 
                FLG_Z_LD <= '1';
                FLG_C_CLR <= '1';                 
                
            --ARITHMETIC--
            when "0000100" =>         --ADD
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '0';
                ALU_SEL <= "0000";
                FLG_Z_LD <= '1';
                FLG_C_LD <= '1';
            when "0000101" =>         --ADDC
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '0';
                ALU_SEL <= "0001";
                FLG_Z_LD <= '1';
                FLG_C_LD <= '1';
            when "0000110" =>         --SUB 
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '0';
                ALU_SEL <= "0010";
                FLG_Z_LD <= '1';
                FLG_C_LD <= '1';
            when "0000111" =>         --SUBC
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '0';
                ALU_SEL <= "0011";
                FLG_Z_LD <= '1';
                FLG_C_LD <= '1';            
            -- --
            when "0001000" =>        --CMP
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '0';
                ALU_SEL <= "0100";
                FLG_Z_LD <= '1';
                FLG_C_LD <= '1';
            when "0001001"  =>     --MOV  
               RF_WR <= '1';
               RF_WR_SEL <= "00";
               ALU_OPY_SEL <= '0'; 
               ALU_SEL <= "1110";
            
            
            ---Reg/Imm---
            --LOGICAL--
            when "1000000"|"1000001"|"1000010"|"1000011" =>   --AND
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '1';
                ALU_SEL <= "0101";
                FLG_Z_LD <= '1';
                FLG_C_CLR <= '1';    
    
            when "1000100"|"1000101"|"1000110"|"1000111" =>   --OR
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '1';
                ALU_SEL <= "0110";
                FLG_Z_LD <= '1';
                FLG_C_CLR <= '1';    
           
            when "1001000"|"1001001"|"1001010"|"1001011" =>   --EXOR
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '1';
                ALU_SEL <= "0111";
                FLG_Z_LD <= '1';
                FLG_C_CLR <= '1';
                    
            when "1001100"|"1001101"|"1001110"|"1001111" =>   --TEST
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '1';
                ALU_SEL <= "1000";
                FLG_Z_LD <= '1';
                FLG_C_CLR <= '1';    
                
            --ARITHMETIC--    
            when "1010000"|"1010001"|"1010010"|"1010011" =>   --ADD
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '1';
                ALU_SEL <= "0000";
                FLG_Z_LD <= '1';
                FLG_C_LD <= '1';
                
            when "1010100"|"1010101"|"1010110"|"1010111" =>   --ADDC 
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '1';
                ALU_SEL <= "0001";
                FLG_Z_LD <= '1';
                FLG_C_LD <= '1';
                
            when "1011000"|"1011001"|"1011010"|"1011011" =>   --SUB 
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '1';
                ALU_SEL <= "0010";
                FLG_Z_LD <= '1';
                FLG_C_LD <= '1';
                
            when "1011100"|"1011101"|"1011110"|"1011111" =>   --SUBC
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '1';
                ALU_SEL <= "0011";
                FLG_Z_LD <= '1';
                FLG_C_LD <= '1';
                
            when "1100000"|"1100001"|"1100010"|"1100011" =>   --CMP
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '1';
                ALU_SEL <= "0100";
                FLG_Z_LD <= '1';
                FLG_C_LD <= '1';
                
            when "1100100"|"1100101"|"1100110"|"1100111" =>   --IN
                RF_WR <= '1';
                RF_WR_SEL <= "11";
            when "1101100"|"1101101"|"1101110"|"1101111" =>    --MOV 
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_OPY_SEL <= '1';
                ALU_SEL <= "1110"; 
            
            when "0100100" =>      --asr
                RF_WR <= '1';
                RF_WR_SEL <= "00";
                ALU_SEL <= "1101";
                FLG_Z_LD <= '1';
                FLG_C_LD <= '1';
                
          

            when "1101000"|"1101001"|"1101010"|"1101011" =>               --OUT
                IO_STRB <= '1';
            when "0010000"  =>            --BRN
                PC_MUX_SEL <= "00";
                PC_LD <= '1';
            when "0010101" =>             --BRCC
                if (C_FLAG = '0') then
                   PC_MUX_SEL <= "00";
                   PC_LD <= '1';
                end if;
            when "0010100" =>             --BRCS
                if (C_FLAG = '1') then
                   PC_MUX_SEL <= "00";
                   PC_LD <= '1';
                end if;
            when "0010010" =>             --BREQ
                if (Z_FLAG = '1') then
                   PC_MUX_SEL <= "00";
                   PC_LD <= '1';
                end if;
            when "0010011" =>             --BRNE
                if (Z_FLAG = '0') then
                   PC_MUX_SEL <= "00";
                   PC_LD <= '1';
                end if;
            when "0110000" =>             --CLC
                FLG_C_CLR <= '1';
            When "0100000" =>  --LSL
	           RF_WR <= '1';
     	       RF_WR_SEL <= "00";
	           ALU_SEL <= "1001";
		       ALU_OPY_SEL <= '0';
               FLG_Z_LD <= '1';
		       FLG_C_LD <= '1'; 
            When "0100001" => --LSR
	           RF_WR <= '1';
           	   RF_WR_SEL <= "00";
	           ALU_SEL <= "1010";
		       ALU_OPY_SEL <= '0';
               FLG_Z_LD <= '1';
               FLG_C_LD <= '1'; 
            When "0100010" => --ROL
	           RF_WR <= '1';
	           RF_WR_SEL <= "00";
	           ALU_SEL <= "1011";
		       ALU_OPY_SEL <= '0';
               FLG_Z_LD <= '1';
               FLG_C_LD <= '1';  
            When "0100011" =>   --ROR
               RF_WR <= '1';
               RF_WR_SEL <= "00";
			   ALU_SEL <= "1100";
		       ALU_OPY_SEL <= '0';
               FLG_Z_LD <= '1';
               FLG_C_LD <= '1';   
            When "0110001" =>    --SEC
               FLG_C_SET <= '1';

            
            --SCR RAM Commands--
            when "1110000"|"1110001"|"1110010"|"1110011" =>    --LD reg -- imm
               RF_WR <= '1';
               RF_WR_SEL <= "01";
               SCR_ADDR_SEL <= "01";
               
            when "0001010" =>   --LD reg -- reg
               RF_WR <= '1';    
               RF_WR_SEL <= "01";
               SCR_ADDR_SEL <= "00";
               
            when "1110100"|"1110101"|"1110110"|"1110111" =>    --ST reg -- imm
               SCR_WE <= '1';
               SCR_DATA_SEL <= '0';
               SCR_ADDR_SEL <= "01";
               
            when "0001011" => --ST reg -- reg
               SCR_WE <= '1';
               SCR_DATA_SEL <= '0';
               SCR_ADDR_SEL <= "00";
            
            when "0010001" => --CALL
               SCR_DATA_SEL <= '1';
               PC_LD <= '1';
               PC_MUX_SEL <= "00";
               SCR_WE <= '1';
               SCR_ADDR_SEL <= "11";
               SP_DECR <= '1';
            when "0110010" => --RET 
               PC_LD <= '1';
               PC_MUX_SEL <= "01";
               SCR_ADDR_SEL <= "10";
               SP_INCR <= '1';
               
            when "0100101" => --Push
               SP_DECR <= '1';
               SCR_ADDR_SEL <= "11";
               SCR_WE <= '1';
               SCR_DATA_SEL <= '0';
               
            when "0100110" => --POP
               SP_INCR <= '1';
               SCR_ADDR_SEL <= "10";
               RF_WR_SEL <= "01";
               RF_WR <= '1';
               
            when "0101000" => --wsp
               SP_LD <= '1';
               
            when "0101001" => --rsp
               RF_WR <= '1';
               RF_WR_SEL <= "10";
               
            
------------interupts---------------------------
            when "0110100" => --sei
               I_SET <= '1';
            when "0110101" =>  --cli
               I_CLR <= '1';
            when "0110110" => --retid
               PC_LD <= '1';
               PC_MUX_SEL <= "01";
               SCR_ADDR_SEL <= "10";
               SP_INCR <= '1';
              FLG_LD_SEL <= '1';
              I_CLR <= '1';
            when "0110111" => --retie
               PC_LD <= '1';
               PC_MUX_SEL <= "01";
               SCR_ADDR_SEL <= "10";
               SP_INCR <= '1';
               FLG_LD_SEL  <= '1';
               I_SET <= '1';
            when others =>      
            end case;
        end case;
    end process executer;
end Behavioral;


