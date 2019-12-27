----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/03/2019 04:08:45 PM
-- Design Name: 
-- Module Name: I_FLAG - Behavioral
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

entity I_FLAG is
    Port ( I_SET : in STD_LOGIC;
           CLK : in STD_LOGIC;
           I_CLR : in STD_LOGIC;
           OUTPUT : out STD_LOGIC);
end I_FLAG;

architecture Behavioral of I_FLAG is

signal I_FLAG : std_logic := '0';

begin

OUTPUT <= I_FLAG;
process (CLK, I_SET, I_CLR)
    begin
        if rising_edge(CLK) then
            if I_CLR = '1' then
                I_FLAG <= '0';
            elsif I_SET = '1' then
                I_FLAG <= '1';
        end if;
        end if;
end process;

end Behavioral;
