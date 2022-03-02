library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity ALU_tb is
end ALU_tb;

architecture Behavioral of ALU_tb is
    component ALU is
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
    --inputs
    signal A,B,Cin,S0,S1,S2 : std_logic := '0';
    --outputs
    signal Cout,G : std_logic := '0';
        
begin
    UUT: ALU
    Port Map(
        A => A,
        B => B,
        Cin => Cin,
        S0 => S0,
        S1 => S1,
        S2 => S2,
        Cout => Cout,
        G => G
    );
sim_proc :process
begin
       	A <= '0'; --0's only
       	B <= '0';
       	Cin <= '0';
       	S0 <= '0';
       	S1 <= '0';
        wait for 5ns;
        
        --Arithmetic Unit Tests:  
        --A      
        A <= '1';
        B <= '0';
        Cin <= '0';
        S0 <= '0';
        S1 <= '0';
        S2 <= '0';
        wait for 5ns;        
        --A+1
        A <= '1';
        B <= '0';
        Cin <= '1';
        S0 <= '0';
        S1 <= '0';
        S2 <= '0';
        wait for 5ns;
        --A+B
        A <= '1';
        B <= '1';
        Cin <= '0';
        S0 <= '1';
        S1 <= '0';
        S2 <= '0';
        wait for 5ns;
        --A+B+1
        A <= '1';
        B <= '1';
        Cin <= '1';
        S0 <= '1';
        S1 <= '0';
        S2 <= '0';
        wait for 5ns;
        --A+B'
        A <= '1';
        B <= '0';
        Cin <= '0';
        S0 <= '0';
        S1 <= '1';
        S2 <= '0';
        wait for 5ns;        
        --A+B'+1
        A <= '1';
        B <= '0';
        Cin <= '1';
        S0 <= '0';
        S1 <= '1';
        S2 <= '0';
        wait for 5ns;
        --A-1
        A <= '1';
        B <= '0';
        Cin <= '0';
        S0 <= '1';
        S1 <= '1';
        S2 <= '0';
        wait for 5ns;
        --A
        A <= '0';
        B <= '0';
        Cin <= '1';
        S0 <= '1';
        S1 <= '1';
        S2 <= '0';
        wait for 5ns;
        --Logic Unit Tests:        
        --A and B
        A <= '1';
        B <= '1';
        Cin <= '0';
        S0 <= '0';
        S1 <= '0';
        S2 <= '1';
        wait for 5ns;        
        --A or B 
        A <= '1';
        B <= '0';
        Cin <= '0';
        S0 <= '1';
        S1 <= '0';
        S2 <= '1';
        wait for 5ns;
        --A xor B
        A <= '1';
        B <= '0';
        Cin <= '0';
        S0 <= '0';
        S1 <= '1';
        S2 <= '1';
        wait for 5ns;
        --not A
        A <= '1';
        B <= '0';
        Cin <= '0';
        S0 <= '1';
        S1 <= '1';
        S2 <= '1';
        wait for 5ns;
     end process;
end Behavioral;
