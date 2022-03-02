library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
 
entity ripple_adder_16_bit is
Port ( 
x : in STD_LOGIC_VECTOR (15 downto 0);
y : in STD_LOGIC_VECTOR (15 downto 0);
z : in STD_LOGIC;
s : out STD_LOGIC_VECTOR (15 downto 0);
c : out STD_LOGIC;
V : out STD_lOGIC);
end ripple_adder_16_bit;
architecture Behavioral of ripple_adder_16_bit is
 component full_adder
Port ( x : in STD_LOGIC;
y : in STD_LOGIC;
z : in STD_LOGIC;
s : out STD_LOGIC;
c : out STD_LOGIC);
end component;

signal c1,c2,c3,c4,c5,c6,c7,c8,c9,c10,c11,c12,c13,c14,c15,c16: STD_LOGIC;
 
begin

fa1: full_adder port map( x(0), y(0), z, s(0), c1);
fa2: full_adder port map( x(1), y(1), c1, s(1), c2);
fa3: full_adder port map( x(2), y(2), c2, s(2), c3);
fa4: full_adder port map( x(3), y(3), c3, s(3), c4);
fa5: full_adder port map( x(4), y(4), c4, s(4), c5);
fa6: full_adder port map( x(5), y(5), c5, s(5), c6);
fa7: full_adder port map( x(6), y(6), c6, s(6), c7);
fa8: full_adder port map( x(7), y(7), c7, s(7), c8);
fa9: full_adder port map( x(8), y(8), c8, s(8), c9);
fa10: full_adder port map( x(9), y(9), c9, s(9), c10);
fa11: full_adder port map( x(10), y(10), c10, s(10), c11);
fa12: full_adder port map( x(11), y(11), c11, s(11), c12);
fa13: full_adder port map( x(12), y(12), c12, s(12), c13);
fa14: full_adder port map( x(13), y(13), c13, s(13), c14);
fa15: full_adder port map( x(14), y(14), c14, s(14), c15);
fa16: full_adder port map( x(15), y(15), c15, s(15), c);
V <= c15 xor c16;
c <= c16;
end Behavioral;