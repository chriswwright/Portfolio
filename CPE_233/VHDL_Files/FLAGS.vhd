----------------------------------------------------------------------------------
-- Engineer: Yu Asai
-- Create Date: 02/07/2019 11:47:39 PM
-- Module Name: FLAGS - Behavioral
----------------------------------------------------------------------------------
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity FLAGS is
  Port (Z : in STD_LOGIC;
        FLG_Z_LD : in STD_LOGIC;
        CLK : in STD_LOGIC;
        Z_FLAG : out STD_LOGIC;
        C : in STD_LOGIC;
        FLG_C_SET : in STD_LOGIC;
        FLG_C_LD : in STD_LOGIC;
        FLG_C_CLR : in STD_LOGIC;
        C_FLAG : out STD_LOGIC
  );
end FLAGS;

architecture Behavioral of FLAGS is



begin
    process(Z, FLG_Z_LD, CLK, C, FLG_C_SET, FLG_C_LD, FLG_C_CLR)
    begin
        if FLG_C_CLR = '1' then
            C_FLAG <= '0';
        elsif FLG_C_SET = '1' then
           C_FLAG <= '1';
        elsif rising_edge(clk) then
            if FLG_C_LD = '1' then
                C_FLAG <= C;
            end if;
        end if;

        if (rising_edge(CLK)) then 
            if FLG_Z_LD = '1' then
               Z_FLAG <= Z;
            end if;
        end if;      
    end process;
            
  
end Behavioral;
