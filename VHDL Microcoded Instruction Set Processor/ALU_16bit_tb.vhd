library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity ALU_16bit_tb is
end ALU_16bit_tb;

architecture Behavioral of ALU_16bit_tb is
    component ALU_16bit is
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
    end component;
    --inputs
    signal A,B : std_logic_vector(15 downto 0) := x"0000";
    signal Gselect : std_logic_vector(3 downto 0) := "0000";
    --outputs
    signal F : std_logic_vector(15 downto 0) := x"0000";
    signal V,C,N,Z : std_logic := '0';
begin
    UUT: ALU_16bit
		Port Map(
		    A => A,
		    B => B,
		    Gselect => Gselect,
		    F => F,
		    V => V,
		    C => C,
		    N => N,
		    Z => Z
		);
sim_proc :process
begin    
    --F=A
    Gselect <= "0000";
    A <= x"000F";
    B <= x"0001";
    wait for 20ns;
    
    --F=A+1
    Gselect <= "0001";
    A <= x"0009";
    B <= x"0011";
    wait for 20ns;
    
   --F=A+B
   Gselect <= "0010";
   A <= x"0004";
   B <= x"0004";
   wait for 20ns;
    
   --F=A+B+1
   Gselect <= "0011";
   A <= x"0003";
   B <= x"0006";
   wait for 20ns;
    
   --F=A+B'
   Gselect <= "0100";
   A <= x"0010";
   B <= x"FFDF";
   wait for 20ns;
    
   --F=A+B'+1
   Gselect <= "0101";
   A <= x"0010";
   B <= x"FFDF";
   wait for 20ns;
    
   --F=A-1
   Gselect <= "0110";
   A <= x"0002";
   B <= x"FF00";
   wait for 20ns;
    
   --F=A
   Gselect <= "0111";
   A <= x"3333";
   B <= x"2222";
   wait for 10ns;
   
  --F=A^B
  Gselect <= "1000";
  A <= x"0FFF";
  B <= x"0F00";
  wait for 10ns;
    
  --F=AorB
  Gselect <= "1010";
  A <= x"0001";
  B <= x"FFF0";
  wait for 10ns;
    
  --F=AxorB
  Gselect <= "1100";
  A <= x"0FF0";
  B <= x"0FD1";
  wait for 10ns;
    
  --F=A'
  Gselect <= "1110";
  A <= x"0001";
  B <= x"0003";
  wait for 10ns;
  --Flags:    
  --V = 1(Overflow)
  Gselect <= "0010";
  A <= x"DFFD";
  B <= x"80F0";  
  wait for 5ns;
  --V = 0(No Overflow)
  Gselect <= "0010";
  A <= x"0001";
  B <= x"0000";  
  wait for 5ns; 

  --C = 1
  Gselect <= "0010";
  A <= x"FFFF";
  B <= x"F300";  
  wait for 5ns;
  --C = 0
  Gselect <= "0010";
  A <= x"0010";
  B <= x"0001";   
  wait for 5ns;
  
  --N = 1
  Gselect <= "0010";
  A <= x"F510";
  B <= x"00D3";  
  wait for 5ns; 
  --N = 0
  Gselect <= "0010";
  A <= x"0010";
  B <= x"000F";  
  wait for 5ns;
  
  --Z = 1
   Gselect <= "0010";
   A <= x"0000";
   B <= x"0000";  
   wait for 5ns; 
   --Z = 0
   Gselect <= "0010";
   A <= x"0020";
   B <= x"0010";  
   wait for 5ns;
end process;
    
end Behavioral;
