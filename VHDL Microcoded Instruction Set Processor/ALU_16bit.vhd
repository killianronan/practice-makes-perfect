library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
entity ALU_16bit is
 Port (
	   A: in std_logic_vector(15 downto 0);       
	   B: in std_logic_vector(15 downto 0);       
	   Gselect: in std_logic_vector(3 downto 0);  	  
	   F: out std_logic_vector(15 downto 0);     
	   V : out std_logic;                        
	   C : out std_logic;                   
	   N : out std_logic;						
	   Z : out std_logic						  
   );
end alu_16bit;
architecture Behavioral of alu_16bit is
	component ALU
		Port ( 
			Cin : in std_logic;
			A: in std_logic;
			B: in std_logic;
			S0: in std_logic;
			S1: in std_logic;
			S2: in std_logic;
			G: out std_logic;
			Cout : out std_logic
		);
	end component;
	component zero_detect
		Port ( 
			I: in std_logic_vector(15 downto 0);
			zero: out std_logic
		); 
	end component;
	--Internal Signals
	signal C1, C2, C3, C4, C5, C6, C7, C8, C9, C10, C11, C12, C13, C14, C15, Cout, Zout : std_logic;
	--Output
	signal result : std_logic_vector(15 downto 0);
begin
    ALU1: ALU
        Port map(
            Cin => Gselect(0), 
            A => A(0), 
            B => B(0), 
            S0 => Gselect(1), 
            S1 => Gselect(2),
            S2 => Gselect(3),
            G => result(0),
            Cout => C1
         );
    ALU2: ALU
        Port map(
            Cin => C1, 
            A => A(1), 
            B => B(1), 
            S0 => Gselect(1), 
            S1 => Gselect(2),
            S2 => Gselect(3),
            G => result(1),
            Cout => C2
         );
         
    ALU3: ALU
         Port map(
             Cin => C2, 
             A => A(2), 
             B => B(2), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(2),
             Cout => C3
         );
         
    ALU4: ALU
         Port map(
             Cin => C3, 
             A => A(3), 
             B => B(3), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(3),
             Cout => C4
         );

    ALU5: ALU
         Port map(
             Cin => C4, 
             A => A(4), 
             B => B(4), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(4),
             Cout => C5
         );

    ALU6: ALU
         Port map(
             Cin => C5, 
             A => A(5), 
             B => B(5), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(5),
             Cout => C6
         );

    ALU7: ALU
         Port map(
             Cin => C6, 
             A => A(6), 
             B => B(6), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(6),
             Cout => C7
         );

    ALU8: ALU
         Port map(
             Cin => C7, 
             A => A(7), 
             B => B(7), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(7),
             Cout => C8
         );

    ALU9: ALU
         Port map(
             Cin => C8, 
             A => A(8), 
             B => B(8), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(8),
             Cout => C9
         );
    ALU10: ALU
         Port map(
             Cin => C9, 
             A => A(9), 
             B => B(9), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(9),
             Cout => C10
         );

    ALU11: ALU
         Port map(
             Cin => C10, 
             A => A(10), 
             B => B(10), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(10),
             Cout => C11
         );

    ALU12: ALU
         Port map(
             Cin => C11, 
             A => A(11), 
             B => B(11), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(11),
             Cout => C12
         );

    ALU13: ALU
         Port map(
             Cin => C12, 
             A => A(12), 
             B => B(12), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(12),
             Cout => C13
         );

    ALU14: ALU
         Port map(
             Cin => C13, 
             A => A(13), 
             B => B(13), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(13),
             Cout => C14
         );

    ALU15: ALU
         Port map(
             Cin => C14, 
             A => A(14), 
             B => B(14), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(14),
             Cout => C15
         );

    ALU16: ALU
         Port map(
             Cin => C15, 
             A => A(15), 
             B => B(15), 
             S0 => Gselect(1), 
             S1 => Gselect(2),
             S2 => Gselect(3),
             G => result(15),
             Cout => Cout
         );
    
    F <= result;
    z_detect: zero_detect
        Port map (
            I => result,
            zero => Zout
        ); 
    V <= (C15 xor Cout) after 5ns;
    C <= Cout after 5ns;
    N <= result(15) after 5ns;
    Z <= Zout after 5ns;
end Behavioral;