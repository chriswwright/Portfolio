----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 01/16/2019 09:25:02 AM
-- Design Name: 
-- Module Name: ProgROM - Behavioral
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

entity ProgramCounter is
  Port ( FROM_IMMED : in STD_LOGIC_VECTOR (9 downto 0);
         FROM_STACK : in STD_LOGIC_VECTOR (9 downto 0);
         Sel : in STD_LOGIC_VECTOR(1 downto 0);
         PC_LD : in STD_LOGIC;
         PC_INC : in STD_LOGIC;
         RST : in STD_LOGIC;
         CLK : in STD_LOGIC;
         PC_Count : out STD_LOGIC_VECTOR (9 downto 0));
end ProgramCounter;

architecture Behavioral of ProgramCounter is


signal max : STD_LOGIC_VECTOR (9 downto 0) := "1111111111";
signal ground : STD_LOGIC_VECTOR (9 downto 0) := "0000000000";

signal mux_out : STD_LOGIC_VECTOR (9 downto 0);
signal count : STD_LOGIC_VECTOR (9 downto 0) := "0000000000";


component MUX_4_to_1
    Port( A : in STD_LOGIC_VECTOR (9 downto 0);
         B : in STD_LOGIC_VECTOR (9 downto 0);
         C : in STD_LOGIC_VECTOR (9 downto 0);
         D : in STD_LOGIC_VECTOR (9 downto 0);
         Sel : in STD_LOGIC_VECTOR(1 downto 0);
         Output : out STD_LOGIC_VECTOR (9 downto 0));
end component;

begin

sel_mux : MUX_4_to_1
port map(A => FROM_IMMED,
         B => FROM_STACK,
         C => max,
         D => ground,
         Sel => Sel,
         Output => mux_out);

process(PC_LD, PC_INC, RST, CLK)
begin

if(rising_edge(CLK)) then

	PC_Count <= count;

	if(RST = '1') then count <= "0000000000";
	elsif(PC_LD = '1') then count <= mux_out;
	elsif(PC_INC = '1') then count <= std_logic_vector(unsigned(count) + 1);
	end if;
end if;

end process;

end Behavioral;


