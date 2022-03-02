library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
entity arithmetic_unit_tb is
end arithmetic_unit_tb;

architecture Behavioral of arithmetic_unit_tb is
component arithmetic_unit is
    Port ( 
        a: in std_logic;
        b: in std_logic;
        Cin: in std_logic;
        Cout: out std_logic;
        S0: in std_logic;
        S1: in std_logic;
        Z: out std_logic
    );
    end component;
    --inputs
    signal a,b,Cin,s0,s1 : std_logic := '0';
     --outputs
     signal c,z : std_logic := '0';
begin
    UUT: arithmetic_unit
    Port Map(
        a => a,
        b => b,
        Cin => Cin,
        S0 => s0,
        S1 => s1,
        Cout => c,
        Z => z
    );
    sim_proc : process
    begin
        a <= '0';
        b <= '0';
        Cin <= '0';
        S0 <= '0';
        S1 <= '0';
        wait for 2ns;
        --F=A
       	a <= '1';
       	b <= '0';
       	Cin <= '0';
       	s0 <= '0';
       	s1 <= '0';
        wait for 2ns;
        --F=A+1
        a <= '1';
        b <= '0';
        Cin <= '1';
        s0 <= '0';
        s1 <= '0';
        wait for 2ns;
        --F=A+B
        a <= '1';
        b <= '1';
        Cin <= '0';
        s0 <= '1';
        s1 <= '0';
        wait for 2ns;
        --F=A+B+1
        a <= '1';
        b <= '1';
        Cin <= '1';
        S0 <= '1';
        S1 <= '0';
        wait for 2ns;
        --F=A+B'
        a <= '1';
        b <= '1';
        Cin <= '0';
        s0 <= '0';
        s1 <= '1';
        wait for 2ns;
        --F=A+B'+1
        a <= '1';
        b <= '1';
        Cin <= '1';
        s0 <= '0';
        s1 <= '1';
        wait for 2ns;
        --F=A-1
        a <= '1';
        b <= '0';
        Cin <= '0';
        s0 <= '1';
        s1 <= '1';
        wait for 2ns;
        --F=A
        a <= '1';
        b <= '0';
        Cin <= '1';
        s0 <= '1';
        s1 <= '1';
        wait for 2ns;
     end process;
end Behavioral;
