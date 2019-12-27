----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 01/30/2019 11:24:00 AM
-- Design Name: 
-- Module Name: LOGIC_MOD - Behavioral
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

entity LOGIC_MOD is
    Port ( SEL : in STD_LOGIC_VECTOR (1 downto 0);
           A : in STD_LOGIC_VECTOR (7 downto 0);
           B : in STD_LOGIC_VECTOR (7 downto 0);
           RESULT : out STD_LOGIC_VECTOR (7 downto 0);
           C : out STD_LOGIC;
           Z : out STD_LOGIC);
end LOGIC_MOD;

architecture Behavioral of LOGIC_MOD is

signal PROTO_RESULT : STD_LOGIC_VECTOR(7 downto 0);

begin

process(SEL, A, B, PROTO_RESULT)
begin
if (SEL = "01") then
    PROTO_RESULT <= A and B;
    if (PROTO_RESULT = "00000000") then
        Z <= '1';
    else
        Z <= '0';
    end if;
    C <= '0';
    
elsif (SEL = "10") then
    PROTO_RESULT <= A or B;
    if (PROTO_RESULT = "00000000") then
       Z <= '1';
    else
       Z <= '0';
    end if;
    C <= '0';

elsif (SEL = "11") then
    PROTO_RESULT <= A xor B;
    if (PROTO_RESULT = "00000000") then
       Z <= '1';
    else
       Z <= '0';
    end if;
    C <= '0';
else 
    PROTO_RESULT <= "00000000";
    Z <= '0';
    C <= '0';
end if;
end process;
RESULT <= PROTO_RESULT;
end Behavioral;
