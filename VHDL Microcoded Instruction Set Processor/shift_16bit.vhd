library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity shift_16bit is
Port (
    bits : in STD_LOGIC_VECTOR (15 downto 0);
    FS : in STD_LOGIC_VECTOR (4 downto 0);
    lR : in STD_LOGIC;
    lL : in STD_LOGIC;
    H : out STD_LOGIC_VECTOR (15 downto 0)
);
end shift_16bit;

architecture Behavioral of shift_16bit is
component shift
Port ( 
In0 : in std_logic;
In1 : in std_logic;
In2 : in std_logic;
sel : in std_logic_vector(1 downto 0);
z : out std_logic);
end component;
signal Sel : std_logic_vector(1 downto 0);

begin
Sel <= "01" when FS = "10100" 
else "10" when FS = "11000" 
else "00" after 5 ns; 

s0: shift port map(
		In0 => bits(0),
		In1 => bits(1),
		In2 => Ll,
		sel => Sel,
		z => H(0));	
s1: shift port map(
		In0 => bits(1),
		In1 => bits(2),
		In2 => bits(0),
		sel => Sel,
		z => H(1));	
s2: shift port map(
		In0 => bits(2),
		In1 => bits(3),
		In2 => bits(1),
		sel => Sel,
		z => H(2));	
s3: shift port map(
		In0 => bits(3),
		In1 => bits(4),
		In2 => bits(2),
		sel => Sel,
		z => H(3));
s4: shift port map(
		In0 => bits(4),
		In1 => bits(5),
		In2 => bits(3),
		sel => Sel,
		z => H(4));	
s5: shift port map(
		In0 => bits(5),
		In1 => bits(6),
		In2 => bits(4),
		sel => Sel,
		z => H(5)); 
s6: shift port map(
		In0 => bits(6),
		In1 => bits(7),
		In2 => bits(5),
		sel => Sel,
		z => H(6));	
s7: shift port map(
		In0 => bits(7),
		In1 => bits(8),
		In2 => bits(6),
		sel => Sel,
		z => H(7));	
s8: shift port map(
		In0 => bits(8),
		In1 => bits(9),
		In2 => bits(7),
		sel => Sel,
		Z => H(8));	
s9: shift port map(
		In0 => bits(9),
		In1 => bits(10),
		In2 => bits(8),
		sel => Sel,
		z => H(9));
s10: shift port map(
		In0 => bits(10),
		In1 => bits(11),
		In2 => bits(9),
		sel => Sel,
		z => H(10));	
s11: shift port map(
		In0 => bits(11),
		In1 => bits(12),
		In2 => bits(10),
		sel => Sel,
		z => H(11));	
s12: shift port map(
		In0 => bits(12),
		In1 => bits(13),
		In2 => bits(11),
		sel => Sel,
		z => H(12));
s13: shift port map(
		In0 => bits(13),
		In1 => bits(14),
		In2 => bits(12),
		sel => Sel,
		z => H(13));
s14: shift port map(
		In0 => bits(14),
		In1 => bits(15),
		In2 => bits(13),
		sel => Sel,
		z => H(14));
s15: shift port map(
		In0 => bits(15),
		In1 => Lr,
		In2 => bits(14),
		sel => Sel,
		z => H(15));
end Behavioral;
