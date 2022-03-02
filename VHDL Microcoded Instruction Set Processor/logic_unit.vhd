library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
entity logic_unit is
    Port ( 
        s0: in std_logic;
        s1: in std_logic;
        A: in std_logic;
        B: in std_logic;
        G: out std_logic
    );
end logic_unit;

architecture Behavioral of logic_unit is
begin
    G <= (A and B) after 2ns when s1='0' and s0='0' 
    else (A or B) after 2ns when s1='0' and s0='1' 
    else (A xor B) after 2ns when s1='1' and s0='0' 
    else (not A) after 2ns when s1='1' and s0='1'; 
end Behavioral;
