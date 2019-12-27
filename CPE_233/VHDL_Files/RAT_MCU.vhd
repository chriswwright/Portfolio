----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 02/07/2019 04:10:08 PM
-- Design Name: 
-- Module Name: RAT_MCU - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity RAT_MCU is
    Port ( IN_PORT : in STD_LOGIC_VECTOR (7 downto 0);
           RESET : in STD_LOGIC;
           INT_IN : in STD_LOGIC;
           CLK : in STD_LOGIC;
           OUT_PORT : out STD_LOGIC_VECTOR (7 downto 0);
           PORT_ID : out STD_LOGIC_VECTOR(7 downto 0);
           IO_STRB : out STD_LOGIC);
end RAT_MCU;


architecture Behavioral of RAT_MCU is

component MUX_2_to_1_1_bit is
    Port (A : in STD_LOGIC;
          B : in STD_LOGIC;
          Sel : in STD_LOGIC;
          Output : out STD_LOGIC);
end component;

component SHADOW_FLAGS is
  Port (Z_in : in STD_LOGIC;
        C_in : in STD_LOGIC;
        FLAG_SHAD_LD : in STD_LOGIC;
        CLK: in STD_LOGIC;
        SHAD_Z : out STD_LOGIC;
        SHAD_C : out STD_LOGIC);
end component;

component I_FLAG is
    Port ( I_SET : in STD_LOGIC;
           CLK : in STD_LOGIC;
           I_CLR : in STD_LOGIC;
           OUTPUT : out STD_LOGIC);
end component;

component Stack_pointer is
    Port ( DATA : in STD_LOGIC_VECTOR (7 downto 0);
           RST : in STD_LOGIC;
           LD : in STD_LOGIC;
           INCR : in STD_LOGIC;
           DECR : in STD_LOGIC;
           CLK : in STD_LOGIC;
           OUTPUT : out STD_LOGIC_VECTOR (7 downto 0));
end component;
component CONTROL_UNIT is
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
end component;

component FLAGS is
  Port (Z : in STD_LOGIC;
        FLG_Z_LD : in STD_LOGIC;
        CLK : in STD_LOGIC;
        Z_FLAG : out STD_LOGIC;
        C : in STD_LOGIC;
        FLG_C_SET : in STD_LOGIC;
        FLG_C_LD : in STD_LOGIC;
        FLG_C_CLR : in STD_LOGIC;
        C_FLAG : out STD_LOGIC
  );
end component;

component MUX_4_to_1_8 is
    Port ( A : in STD_LOGIC_VECTOR (7 downto 0);
          B : in STD_LOGIC_VECTOR (7 downto 0);
          C : in STD_LOGIC_VECTOR (7 downto 0);
          D : in STD_LOGIC_VECTOR (7 downto 0);
          Sel : in STD_LOGIC_VECTOR(1 downto 0);
          Output : out STD_LOGIC_VECTOR (7 downto 0));
end component;

component MUX_2_to_1 is
    Port ( A : in STD_LOGIC_VECTOR (9 downto 0);
          B : in STD_LOGIC_VECTOR (9 downto 0);
          Sel : in STD_LOGIC;
          Output : out STD_LOGIC_VECTOR (9 downto 0));
end component;

component MUX_2_to_1_8 is
    Port ( A : in STD_LOGIC_VECTOR (7 downto 0);
          B : in STD_LOGIC_VECTOR (7 downto 0);
          Sel : in STD_LOGIC;
          Output : out STD_LOGIC_VECTOR (7 downto 0));
end component;



component ProgramCounter is
  Port ( FROM_IMMED : in STD_LOGIC_VECTOR (9 downto 0);
         FROM_STACK : in STD_LOGIC_VECTOR (9 downto 0);
         Sel : in STD_LOGIC_VECTOR(1 downto 0);
         PC_LD : in STD_LOGIC;
         PC_INC : in STD_LOGIC;
         RST : in STD_LOGIC;
         CLK : in STD_LOGIC;
         PC_Count : out STD_LOGIC_VECTOR (9 downto 0));
end component;

component SCR is
    Port ( DATA_IN : in STD_LOGIC_VECTOR (9 downto 0);
           SCR_ADDR : in STD_LOGIC_VECTOR (7 downto 0);
           SCR_WE : in STD_LOGIC;
           CLK : in STD_LOGIC;
           DATA_OUT : out STD_LOGIC_VECTOR (9 downto 0));
end component;


component REG_FILE is
    Port ( DIN : in STD_LOGIC_VECTOR (7 downto 0);
           ADRX : in STD_LOGIC_VECTOR (4 downto 0);
           ADRY : in STD_LOGIC_VECTOR (4 downto 0);
           RF_WR : in STD_LOGIC;
           CLK : in STD_LOGIC;
           DX_OUT : out STD_LOGIC_VECTOR (7 downto 0);
           DY_OUT : out STD_LOGIC_VECTOR (7 downto 0));
end component;

component prog_rom is 
   port (     ADDRESS : in std_logic_vector(9 downto 0); 
          INSTRUCTION : out std_logic_vector(17 downto 0); 
                  CLK : in std_logic);  
end component;

component ALU is
    Port ( SEL : in STD_LOGIC_VECTOR (3 downto 0);
           A : in STD_LOGIC_VECTOR (7 downto 0);
           B : in STD_LOGIC_VECTOR (7 downto 0);
           CIN : in STD_LOGIC;
           RESULT : out STD_LOGIC_VECTOR (7 downto 0);
           C : out STD_LOGIC;
           Z : out STD_LOGIC);
end component;

--flag signals
signal z_flag, c_flag : std_logic;
signal c, z : std_logic;

--Progrom signal
signal instruction : std_logic_vector(17 downto 0) := "000000000000000000"; -- from immediate is always progrom

--Control Unit Signal
signal I_set, I_clr : std_logic;

--Program Counter Signal
signal PC_LD, PC_INC : std_logic;
signal PC_MUX_SEL : std_logic_vector(1 downto 0);
signal PC_COUNT : std_logic_vector(9 downto 0);

--REG FILE Signal
signal RF_WR_SEL : std_logic_vector(1 downto 0);
signal RF_WR : std_logic;
signal DIN : std_logic_vector(7 downto 0);--needs mux
signal DX_OUT, DY_OUT : std_logic_vector(7 downto 0);

--SCR Signal
signal big_dx_out : std_logic_vector(9 downto 0);
signal SCR_WE : std_logic;
signal SCR_ADDR_SEL : std_logic_vector(1 downto 0);
signal SCR_DATA_SEL : std_logic;
signal Data_OUT : std_logic_vector(9 downto 0);-- from stack
signal Data_IN : std_logic_vector(9 downto 0);--needs mux
signal SCR_ADDR : std_logic_vector(7 downto 0);--needs mux

--ALU signal
signal ALU_OPY_SEL : std_logic;
signal ALU_SEL : std_logic_vector(3 downto 0);
signal ALU_IN_B : std_logic_vector(7 downto 0); --needs mux
signal ALU_RESULT : std_logic_vector(7 downto 0);

--SP signal
signal SP_LD, SP_INCR, SP_DECR : std_logic;
signal dec_pointer, pointer : std_logic_vector(7 downto 0);

--Flag signal
signal FLG_C_SET, FLG_C_CLR, FLG_C_LD, FLG_Z_LD, FLG_LD_SEL, FLG_SHAD_LD : STD_LOGIC; 

--Flag signal
signal RST_OUT : std_logic;
signal SHAD_C : std_logic;
signal SHAD_Z : std_logic;
signal to_c : std_logic;
signal to_z : std_logic;

signal int_out : std_logic;
signal int_control : std_logic;

begin
my_little_mux_c : MUX_2_to_1_1_bit
 port map(SEL => FLG_LD_SEL,
          A => c,
          B => c_flag,
          OUTPUT => to_c
     );

my_little_mux_z : MUX_2_to_1_1_bit
 port map(SEL => FLG_LD_SEL,
          A => z,
          B => z_flag,
          OUTPUT => to_z
          );
my_flags : FLAGS
  port map(Z => to_z,
        FLG_Z_LD => FLG_Z_LD,
        CLK => CLK,
        Z_FLAG => z_flag,
        C => to_c,
        FLG_C_SET => FLG_C_SET,
        FLG_C_LD => FLG_C_LD,
        FLG_C_CLR => FLG_C_CLR,
        C_FLAG => c_flag
        );


my_shad_flag : shadow_flags
 port map(Z_IN => Z_FLAG,
          C_IN => C_FLAG,
          FLAG_SHAD_LD => FLG_SHAD_LD,
          CLK => CLK,
          SHAD_Z => SHAD_Z,
          SHAD_C => SHAD_C
 
 );
 
my_stack_pointer : Stack_pointer
 port map(RST => RST_OUT,
          LD => SP_LD,
          INCR => SP_INCR,
          DECR => SP_DECR,
          DATA => DX_OUT,
          OUTPUT => pointer,
          CLK => clk);
 
my_prog_rom : Prog_rom
 port map(ADDRESS => PC_COUNT,
          INSTRUCTION => INSTRUCTION,
                  CLK => CLK);

my_I_FLAG : I_FLAG
 port map(I_SET => I_SET,
          CLK => CLK,
          I_CLR => I_CLR,
          OUTPUT => Int_out);

int_control <= INT_IN and Int_out;
my_control_unit : CONTROL_UNIT
port map(C_FLAG => c_flag,
        Z_FLAG => z_flag,
        INT_IN => int_control,
        RESET => RESET,
        OPCODE_HI_5 => instruction(17 downto 13),
        OPCODE_LO_2 => instruction(1 downto 0),
        CLK => CLK,
        I_SET => I_set,
        I_CLR => I_clr,
        PC_LD => PC_LD,
        PC_INC => PC_INC,
        PC_MUX_SEL => PC_MUX_SEL, 
        RF_WR_SEL => RF_WR_SEL,
        RF_WR => RF_WR,
        SCR_DATA_SEL => SCR_DATA_SEL,
        SCR_WE => SCR_WE,
        SCR_ADDR_SEL => SCR_ADDR_SEL,
        ALU_OPY_SEL => ALU_OPY_SEL,
        ALU_SEL => ALU_SEL,
        SP_LD => SP_LD,
        SP_INCR => SP_INCR,
        SP_DECR => SP_DECR,
        FLG_C_SET => FLG_C_SET,
        FLG_C_CLR => FLG_C_CLR,
        FLG_C_LD => FLG_C_LD,
        FLG_Z_LD => FLG_Z_LD,
        FLG_LD_SEL => FLG_LD_SEL,
        FLG_SHAD_LD => FLG_SHAD_LD, 
        RST_OUT => RST_OUT,
        IO_STRB => IO_STRB
        );
        
my_counter : ProgramCounter
  port map ( FROM_IMMED => instruction(12 downto 3),
         FROM_STACK =>Data_out,
         Sel =>PC_MUX_SEL,
         PC_LD =>PC_LD,
         PC_INC =>PC_INC,
         RST =>RST_Out,
         CLK =>CLK,
         PC_Count => PC_count
         );

my_reg : REG_FILE
  port map(DIN => DIN,
           ADRX => instruction(12 downto 8),
           ADRY => instruction(7 downto 3),
           RF_WR => RF_WR,
           CLK => CLK,
           DX_OUT => DX_OUT,
           DY_OUT => DY_OUT
  );
  OUT_PORT <= DX_OUT;
  
my_scr : SCR
  port map( DATA_IN => DATA_IN,
           SCR_ADDR => SCR_ADDR,
           SCR_WE => SCR_WE,
           CLK => CLK,
           DATA_OUT => DATA_OUT);
           
my_alu : ALU
  port map(SEL => ALU_SEL,
           A => DX_OUT,
           B => ALU_IN_B,
           CIN => C_Flag,
           RESULT => ALU_RESULT,
           C => c,
           Z => z
           );
           

        
reg_mux : MUX_4_to_1_8
    port map ( A => ALU_RESULT,
          B => DATA_OUT(7 downto 0), 
          C => pointer, -- sp
          D => In_port,
          Sel => RF_WR_SEL,
          Output => DIN);

dec_pointer <= std_logic_vector(unsigned(pointer) - 1);
scr_addr_mux : MUX_4_to_1_8
   port map ( A => DY_OUT,
          B => instruction(7 downto 0),
          C => pointer, -- sp
          D => dec_pointer, -- sp
          Sel => SCR_ADDR_SEL,
          Output => SCR_ADDR);
          
port_id <= instruction(7 downto 0);
big_dx_out <= "00" & DX_out;
scr_data_in_mux : MUX_2_to_1
   port map (A => big_dx_out,
             B => PC_count,
             sel => SCR_DATA_SEL,
             OUTPUT => DATA_IN
             );
   
alu_B_mux : MUX_2_to_1_8
   port map (A => DY_OUT,
             B => instruction(7 downto 0),
             sel => ALU_OPY_SEL,
             OUTPUT => ALU_IN_B);


end Behavioral;
