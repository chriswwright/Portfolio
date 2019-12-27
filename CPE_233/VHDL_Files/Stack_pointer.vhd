----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 02/22/2019 09:21:56 AM
-- Design Name: 
-- Module Name: Stack_pointer - Behavioral
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

entity Stack_pointer is
    Port ( DATA : in STD_LOGIC_VECTOR (7 downto 0);
           RST : in STD_LOGIC;
           LD : in STD_LOGIC;
           INCR : in STD_LOGIC;
           DECR : in STD_LOGIC;
           CLK : in STD_LOGIC;
           OUTPUT : out STD_LOGIC_VECTOR (7 downto 0));
end Stack_pointer;

architecture Behavioral of Stack_pointer is
signal Pointer : std_logic_vector(7 downto 0) := "00000000";
begin

process(CLK, RST, LD, DECR, INCR)
begin
if rising_edge(CLK) then
   if RST = '1' then
      Pointer <= "00000000";
   elsif INCR = '1' then
      Pointer <= std_logic_vector(unsigned(Pointer) + 1);
   elsif DECR = '1' then
      Pointer <= std_logic_vector(unsigned(Pointer) - 1);
   elsif LD = '1' then
      Pointer <= Data;
   else Pointer <= Pointer;
   end if;
end if;
end process;
OUTPUT <= Pointer;
end Behavioral;
