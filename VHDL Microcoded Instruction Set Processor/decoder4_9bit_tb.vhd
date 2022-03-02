library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity decoder4_9bit_tb is
end decoder4_9bit_tb;

architecture Behavioral of decoder4_9bit_tb is

COMPONENT decoder4_9bit
Port(   des :in std_logic_vector(3 downto 0);
        RW : in std_logic;
        Q0 : out std_logic;
        Q1 : out std_logic; 
        Q2 : out std_logic;
        Q3 : out std_logic;
        Q4 : out std_logic;
        Q5 : out std_logic;
        Q6 : out std_logic;
        Q7 : out std_logic;
        Q8 : out std_logic);
END COMPONENT;
   --Inputs
   signal des : std_logic_vector(3 downto 0) :=  "0000";
   signal RW : std_logic :=  '0';
   --Outputs
   signal Q0 : std_logic :=  '0';
   signal Q1 : std_logic :=  '0';
   signal Q2 : std_logic :=  '0';
   signal Q3 : std_logic :=  '0';
   signal Q4 : std_logic :=  '0';
   signal Q5 : std_logic :=  '0';
   signal Q6 : std_logic :=  '0';
   signal Q7 : std_logic :=  '0';
   signal Q8 : std_logic :=  '0';
begin
  UUT: decoder4_9bit Port Map(
        des => des,
        RW => RW,
        Q0 => Q0,
        Q1 => Q1,
        Q2 => Q2,
        Q3 => Q3,
        Q4 => Q4,
        Q5 => Q5,
        Q6 => Q6,
        Q7 => Q7,
        Q8 => Q8
   );
   sim_proc: process
 begin
    RW <= '1';
    des <= "0000";
    wait for 5 ns;
    des <= "0001";
    wait for 5 ns;
    des <= "0010";
    wait for 5 ns;
    des <= "0011";
    wait for 5 ns;
    des <= "0100";
    wait for 5 ns;    
    des <= "0101";
    wait for 5 ns;    
    des <= "0110";
    wait for 5 ns;
    des <= "0111";
    wait for 5 ns;
    des <= "1000";
    wait for 5 ns;
end process;
end Behavioral;
