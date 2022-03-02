

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
 
entity full_adder is
Port ( 
    x,y ,z : in std_logic;
    s,c : out std_logic
);
end full_adder;
architecture Behavioral of full_adder is
    signal s0, s1, s2 : std_logic;
begin
	 s <= (x xor y) xor z after 1ns;
     c <= ((x xor y) and z) or (x and y) after 1ns;
end Behavioral;
