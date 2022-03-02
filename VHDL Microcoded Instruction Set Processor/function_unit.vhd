library IEEE;
use IEEE.std_logic_1164.ALL;

entity function_unit is
	Port ( 
		A : in std_logic_vector(15 downto 0);
		B : in std_logic_vector(15 downto 0);
		FS : in std_logic_vector(4 downto 0);
		V : out std_logic;
		C : out std_logic;
		N : out std_logic;
		Z : out std_logic;	
		F : out std_logic_vector(15 downto 0)
	);
end function_unit;

architecture Behavioral of function_unit is
	component ALU_16bit
		Port(
			A: in std_logic_vector(15 downto 0);       
	   		B: in std_logic_vector(15 downto 0);       
	   		Gselect: in std_logic_vector(3 downto 0);   
		   	F: out std_logic_vector(15 downto 0);     
		   	V : out std_logic;                         
		   	C : out std_logic;                        
		   	N : out std_logic;                         
		   	Z : out std_logic                    
		);
	end component;
	component mux2_16bit
		Port(
			In1 : IN std_logic_vector(15 downto 0);
			In2 : IN std_logic_vector(15 downto 0);
			s : IN std_logic;
			Z : OUT std_logic_vector(15 downto 0)
		);
	end component;
    component shift_16bit
		Port(
			bits : in std_logic_vector (15 downto 0);
			FS : in std_logic_vector (4 downto 0);
			lR : in std_logic;
			lL : in std_logic;
			H : out std_logic_vector (15 downto 0)
		);
	end component;
    signal ALU_out, shift_out, m_out : std_logic_vector(15 downto 0);
begin
	ALU: ALU_16bit 
	Port Map(
		A => A,
		B => B,
		Gselect(0) => FS(0),
		Gselect(1) => FS(1),
		Gselect(2) => FS(2),
		Gselect(3) => FS(3),
		F => ALU_out,
		V => V,
		C => C,
		N => N, 
		Z => Z
	);
	MUXF: mux2_16bit 
	Port Map(
		In1 => ALU_out,
		In2 => shift_out,
		s => FS(4),
		Z => F
	);
	shifter: shift_16bit 
	Port Map(
		bits => B,
		FS => FS,
		Lr => '0',
		Ll => '0',
		H => shift_out
	);	
end Behavioral;