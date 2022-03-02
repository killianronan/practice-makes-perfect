library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
entity logic_unit_tb is
end logic_unit_tb;
architecture Behavioral of logic_unit_tb is
    component logic_unit is
        Port ( 
		    S0: in std_logic;
		    S1: in std_logic;
		    A: in std_logic;
		    B: in std_logic;
		    G: out std_logic
		);
    end component;
    --inputs
    signal A : std_logic := '0';
    signal B : std_logic := '0';
    signal s0 : std_logic := '0'; 
    signal s1 : std_logic := '0'; 
    --outputs
    signal G : std_logic := '0';  
begin
    UUT: logic_unit
    Port Map(
        S0 => S0,
        S1 => S1,
        A => A,
        B => B,
        G => G
    );
sim_proc :process
begin
    A <= '1';--A and B
    B <= '1';
    S0 <= '0';
    S1 <= '0';
    wait for 2ns;
    
    A <= '0';--A or B
    B <= '1';
    S0 <= '1';
    S1 <= '0';
    wait for 2ns;
    
    A <= '0';--A xor B
    B <= '1';
    S0 <= '0';
    S1 <= '1';
    wait for 2ns;
    
    A <= '1';--not A
    B <= '0';
    S0 <= '1';
    S1 <= '1';
    wait for 2ns;
 end process;
end Behavioral;
