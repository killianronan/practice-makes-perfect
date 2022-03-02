library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity instruction_register is
  Port (
       instruction : in std_logic_vector(15 downto 0);
       IL,Clk : in std_logic;
       DR,SA,SB : out std_logic_vector(2 downto 0);
       op_out : out std_logic_vector(6 downto 0)
        );
end instruction_register;

architecture Behavioral of instruction_register is
signal tmp : std_logic_vector(15 downto 0);

begin
tmp <= instruction after 1ns when IL = '1' 
           else tmp after 1ns;
SB <= tmp(2 downto 0)when CLk = '1' ;
SA <= tmp(5 downto 3)when CLk = '1' ;
DR <= tmp(8 downto 6) when CLk = '1';
op_out <= tmp(15 downto 9)when Clk= '1' ;

end Behavioral;
