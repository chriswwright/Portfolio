----------------------------------------------------------------------------------
-- Create Date: 01/23/2019 09:21:32 AM
-- Design Name: 
-- Module Name: REG_FILE - Behavioral

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

entity REG_FILE is
    Port ( DIN : in STD_LOGIC_VECTOR (7 downto 0);
           ADRX : in STD_LOGIC_VECTOR (4 downto 0);
           ADRY : in STD_LOGIC_VECTOR (4 downto 0);
           RF_WR : in STD_LOGIC;
           CLK : in STD_LOGIC;
           DX_OUT : out STD_LOGIC_VECTOR (7 downto 0);
           DY_OUT : out STD_LOGIC_VECTOR (7 downto 0));
end REG_FILE;

architecture Behavioral of REG_FILE is

TYPE memory is ARRAY(0 to 31) of std_logic_vector(7 downto 0);
Signal rom : memory := (others => (others => '0'));

begin
DY_OUT <= rom(to_integer(unsigned(ADRY)));
DX_OUT <= rom(to_integer(unsigned(ADRX)));
process(DIN, ADRX, CLK, RF_WR)
begin

if(rising_edge(CLK))then

    if(RF_WR = '1') then
        rom(to_integer(unsigned(ADRX))) <= DIN;
    end if;
end if;

end process;

end Behavioral;
