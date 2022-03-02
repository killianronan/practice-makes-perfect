library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
entity arithmetic_unit is
    Port ( 
       a: in std_logic;
       b: in std_logic;
       Cin: in std_logic;
       Cout: out std_logic;
       s0: in std_logic;
       s1: in std_logic;
       z: out std_logic
    );
    end arithmetic_unit;

architecture Behavioral of arithmetic_unit is
    component full_adder
        Port ( 
            x: in std_logic;
            y: in std_logic;
            z: in std_logic;
            s: out std_logic;
            c: out std_logic
        );
    end component;
    signal input : std_logic;
begin
    input <= (b and S0) or ((not b) and S1);
    AC: full_adder 
    	Port map (
    		x => a,
    		y => input,
    		z => Cin,
    		s => z,
    		c => Cout
    );
end Behavioral;
