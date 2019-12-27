----------------------------------------------------------------------------------
-- Engineer: Asai_Wright
-- 
-- Create Date: 01/30/2019 09:23:15 AM
-- Design Name: 
-- Module Name: ALU - Behavioral
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

entity ALU is
    Port ( SEL : in STD_LOGIC_VECTOR (3 downto 0);
           A : in STD_LOGIC_VECTOR (7 downto 0);
           B : in STD_LOGIC_VECTOR (7 downto 0);
           CIN : in STD_LOGIC;
           RESULT : out STD_LOGIC_VECTOR (7 downto 0);
           C : out STD_LOGIC;
           Z : out STD_LOGIC);
end ALU;

architecture Behavioral of ALU is

signal C_arith : std_logic;
signal Z_arith : std_logic;
signal RESULT_arith : std_logic_vector(7 downto 0);
signal sel_arith: std_logic_vector(1 downto 0) := "00";

signal C_logic : std_logic;
signal Z_logic : std_logic;
signal RESULT_logic : std_logic_vector(7 downto 0);
signal sel_logic: std_logic_vector(1 downto 0) := "00";

signal C_shift : std_logic;
signal Z_shift : std_logic;
signal RESULT_shift : std_logic_vector(7 downto 0);
signal sel_shift : std_logic_vector(2 downto 0) := "000";


component SHIFT_MOD is
    Port ( A : in STD_LOGIC_VECTOR (7 downto 0);
           B : in STD_LOGIC_VECTOR (7 downto 0);
           SEL : in STD_LOGIC_VECTOR (2 downto 0);
           CIN : in STD_LOGIC;
           C : out STD_LOGIC;
           Z : out STD_LOGIC;
           RESULT : out STD_LOGIC_VECTOR (7 downto 0));
end component;

component LOGIC_MOD is
    Port ( SEL : in STD_LOGIC_VECTOR (1 downto 0);
           A : in STD_LOGIC_VECTOR (7 downto 0);
           B : in STD_LOGIC_VECTOR (7 downto 0);
           RESULT : out STD_LOGIC_VECTOR (7 downto 0);
           C : out STD_LOGIC;
           Z : out STD_LOGIC);
end component;

component MATH_MOD is
    Port ( A : in STD_LOGIC_VECTOR (7 downto 0);
           B : in STD_LOGIC_VECTOR (7 downto 0);
           Cin : in STD_LOGIC;
           SEL : in STD_LOGIC_VECTOR (1 downto 0);
           RESULT : out STD_LOGIC_VECTOR (7 downto 0);
           C : out STD_LOGIC;
           Z : out STD_LOGIC);
end component;

begin

s_mod : SHIFT_MOD
    Port map(
    A => A,
    B => B,
    Cin => Cin,
    SEL => SEL_shift,
    C => C_shift,
    Z => Z_shift,
    RESULT => RESULT_shift
    );

l_mod : LOGIC_MOD
    Port map(
    A => A,
    B => B,
    SEL => SEL_logic,
    RESULT => RESULT_logic,
    C => C_logic,
    Z => Z_logic
    );

m_mod : MATH_MOD
    Port map(
    A => A,
    B => B,
    Cin => Cin,
    SEL => SEL_arith,
    RESULT => RESULT_arith,
    C => C_arith,
    Z => Z_arith
    );
    
process(SEL, C_arith, Z_arith, C_logic, Z_logic, C_shift, Z_shift, RESULT_arith, RESULT_shift, RESULT_logic, A)
begin

if (SEL (3 downto 2) = "00") then
    SEL_arith <= SEL(1 downto 0);
    C <= C_arith;
    Z <= Z_arith;
    SEL_shift <= "000";
    SEL_logic <= "00";
    RESULT <= RESULT_arith;
elsif (SEL = "0100") then
    SEL_arith <= "10";
    C <= C_arith;
    Z <= Z_arith;
    SEL_shift <= "000";
    SEL_logic <= "00";
    RESULT <= A;
elsif (SEL = "0101") or (SEL = "0110") or (SEL = "0111") then
    SEL_logic <= SEL(1 downto 0);
    C <= C_logic;
    Z <= Z_logic;
    SEL_shift <= "000";
    SEL_arith <= "00";
    RESULT <= RESULT_logic;
elsif (SEL = "1000") then
    SEL_logic <= "01";
    C <= C_logic;
    Z <= Z_logic;
    SEL_shift <= "000";
    SEL_arith <= "00";
    RESULT <= A;
elsif (SEL > "1000") then
    SEL_shift <= SEL(2 downto 0);
    RESULT<= RESULT_shift;
    C <= C_shift;
    Z <= Z_shift;
    SEL_logic <= "00";
    SEL_arith <= "00";
else 
    RESULT <= "00000000";
    C <= '0';
    Z <= '0';
    SEL_logic <= "00";
    SEL_arith <= "00";
    SEL_shift <= "000";
end if; 
end process;

end Behavioral;
