library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
use IEEE.NUMERIC_STD.ALL;

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity SCR is
    Port ( DATA_IN : in STD_LOGIC_VECTOR (9 downto 0);
           SCR_ADDR : in STD_LOGIC_VECTOR (7 downto 0);
           SCR_WE : in STD_LOGIC;
           CLK : in STD_LOGIC;
           DATA_OUT : out STD_LOGIC_VECTOR (9 downto 0));
end SCR;

architecture Behavioral of SCR is
    Type memory is ARRAY(0 to 255) of std_logic_vector(9 downto 0);
    signal rom: memory := (others => (others => '0'));

begin

process(CLK,SCR_WE,SCR_ADDR,DATA_IN)
begin    
    if rising_edge(CLK) then
        if (SCR_WE = '1') then
            rom(to_integer(unsigned(SCR_ADDR))) (9 downto 0) <= DATA_IN (9 downto 0);
        end if;
    end if;
end process;    
DATA_OUT <= rom(to_integer(unsigned(SCR_ADDR)));
       
end Behavioral;
