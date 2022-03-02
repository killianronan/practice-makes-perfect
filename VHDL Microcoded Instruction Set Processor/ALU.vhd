library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
entity ALU is
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
end ALU;
architecture Behavioral of ALU is
    component logic_unit
        Port ( 
            S0: in std_logic;
            S1: in std_logic;
            A: in std_logic;
            B: in std_logic;
            G: out std_logic
        );
    end component;
    component arithmetic_unit
        Port ( 
           a: in std_logic;
           b: in std_logic;
           Cin: in std_logic;
           Cout: out std_logic;
           S0: in std_logic;
           S1: in std_logic;
           z: out std_logic
        );
    end component;
    component mux2_1bit
        Port (
            s : in std_logic;
            In1 : in std_logic;
            In2 : in std_logic;
            m_out : out std_logic
        );
    end component;
    signal arith_out, logic : std_logic;
begin
    AU: arithmetic_unit 
        Port map(
            a => a, 
            b => b,
            Cin => Cin,
            Cout => Cout, 
            S0 => S0,
            S1 => S1,
            z => arith_out
        );
    LU: logic_unit 
        Port map(
            S0 => S0, 
            S1 => S1, 
            A => A, 
            B => B, 
            G => logic
        );
    MUX: mux2_1bit 
        Port map(
            In1 => arith_out, 
            In2 => logic, 
            s => S2, 
            m_out => G
        ); 
end Behavioral;