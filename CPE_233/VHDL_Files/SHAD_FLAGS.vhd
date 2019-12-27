----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 03/03/2019 04:26:50 PM
-- Design Name: 
-- Module Name: SHADOW_FLAGS - Behavioral
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

entity SHADOW_FLAGS is
  Port (Z_in : in STD_LOGIC;
        C_in : in STD_LOGIC;
        FLAG_SHAD_LD : in STD_LOGIC;
        CLK: in STD_LOGIC;
        SHAD_Z : out STD_LOGIC;
        SHAD_C : out STD_LOGIC);
end SHADOW_FLAGS;

architecture Behavioral of SHADOW_FLAGS is

begin
    process(Z_in, FLAG_SHAD_LD, CLK, C_in)
    begin
        if rising_edge(CLK) then
            if FLAG_SHAD_LD = '1' then
                SHAD_Z <= Z_in;
                SHAD_C <= C_in;
            end if;
        end if;
    end process;
end Behavioral;

