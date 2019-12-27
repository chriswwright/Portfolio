----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 01/30/2019 10:28:03 AM
-- Design Name: 
-- Module Name: MATH_MOD - Behavioral
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

entity MATH_MOD is
    Port ( A : in STD_LOGIC_VECTOR (7 downto 0);
           B : in STD_LOGIC_VECTOR (7 downto 0);
           CIN : in std_logic;
           SEL : in STD_LOGIC_VECTOR (1 downto 0);
           RESULT : out STD_LOGIC_VECTOR (7 downto 0);
           C : out STD_LOGIC;
           Z : out STD_LOGIC);
end MATH_MOD;

architecture Behavioral of MATH_MOD is
signal A_big : std_logic_vector(8 downto 0);
signal B_big : std_logic_vector(8 downto 0);
signal Cin_mod : std_logic_vector(1 downto 0);
signal RESULT_big : std_logic_vector(8 downto 0);

begin
A_big <= '0' & A;
B_big <= '0' & B;
process(SEL, A, B, Cin, A_big, B_big, Cin_mod, RESULT_big)
begin
if (SEL(0) = '1') then
   Cin_mod <= '0' & CIN;
else Cin_mod <= "00";
end if;
if (SEL(1) = '0') then
   Result_big <= std_logic_vector(unsigned(A_big) + unsigned(B_big) + unsigned(Cin_mod));
   if (Result_big(7 downto 0) = "00000000") then
       Z <= '1';
   else Z<= '0';
   end if;

elsif(SEL(1) = '1') then
   Result_big <= std_logic_vector(unsigned(A_big) - unsigned(B_big) - unsigned(Cin_mod));
   if (Result_big(7 downto 0) = "00000000") then
       Z <= '1';
   else Z<= '0';
   end if;
else
    Result_big <= "000000000";
    Z <= '0';
end if;
   c <= Result_big(8);
   RESULT <= Result_big(7 downto 0);
end process;

end Behavioral;
