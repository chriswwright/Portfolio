----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 01/16/2019 09:25:02 AM
-- Design Name: 
-- Module Name: MUX_4_to_1 - Behavioral
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

entity MUX_2_to_1 is
    Port ( A : in STD_LOGIC_VECTOR (9 downto 0);
          B : in STD_LOGIC_VECTOR (9 downto 0);
          Sel : in STD_LOGIC;
          Output : out STD_LOGIC_VECTOR (9 downto 0));
end MUX_2_to_1;

architecture Behavioral of MUX_2_to_1 is

begin

with Sel select
    Output <= A when '0',
            B when '1',
            "0000000000" when others;
    
end Behavioral;

