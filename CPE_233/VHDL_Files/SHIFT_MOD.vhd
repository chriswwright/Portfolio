----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 01/30/2019 11:51:13 AM
-- Design Name: 
-- Module Name: SHIFT_MOD - Behavioral
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
--use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity SHIFT_MOD is
    Port ( A : in STD_LOGIC_VECTOR (7 downto 0);
           B : in STD_LOGIC_VECTOR (7 downto 0);
           SEL : in STD_LOGIC_VECTOR (2 downto 0);
           Cin : in STD_LOGIC;
           C : out STD_LOGIC;
           Z : out STD_LOGIC;
           RESULT : out STD_LOGIC_VECTOR (7 downto 0));
end SHIFT_MOD;

architecture Behavioral of SHIFT_MOD is

begin

process(SEL, A, B, Cin)
begin
if (SEL = "001") then
   C <= A(7);
   if (A(6 downto 0) & Cin = "00000000") then
       Z <= '1';
   else
       Z <= '0';
   end if;
    RESULT <= A(6 downto 0) & Cin;
elsif (SEL = "010")then
    C <= A(0);
    if (Cin & A(7 downto 1) = "00000000") then
    Z <= '1';
   else
    Z <= '0';
   end if;
    RESULT <= Cin & A(7 downto 1);
elsif (SEL = "011") then
   C <= A(7);
   RESULT <= A(6 downto 0) & A(7);
   if (A(6 downto 0) & A(7) = "00000000") then
   Z <= '1';
   else
   Z <= '0';
   end if;
elsif (SEL = "100") then
   C <= A(0);
   RESULT <= A(0) & A(7 downto 1);
    if (A(0) & A(7 downto 1) = "00000000") then
       Z <= '1';
   else
       Z <= '0';
   end if;
elsif (SEL = "101") then
   C <= A(0);
   if (A(7) & A(7 downto 1) = "00000000") then
       Z <= '1';
   else
       Z <= '0';
   end if;
   RESULT <= A(7) & A(7 downto 1);  
elsif (SEL = "110") then
   RESULT <= B;
   C <= Cin;
   Z <= '0'; --The control unit has Z_LD set to zero independent of z here. This is just to avoid latches.
else
   RESULT <= "00000000";
   C <= '0';
   Z <= '0';
end if;

end process;

end Behavioral;
