
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity sign_extend is
    Port ( SB : in  STD_LOGIC_VECTOR(2 downto 0);
           DR : in  STD_LOGIC_VECTOR(2 downto 0);
           result : out  STD_LOGIC_VECTOR(15 downto 0));
end sign_extend;

architecture Behavioral of sign_extend is

begin
    result(2 downto 0) <= SB;
    result(5 downto 3) <= DR;
    result(15 downto 6) <= "0000000000" when DR(2) = '0' else "1111111111";
end Behavioral;
