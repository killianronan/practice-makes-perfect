----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 21.04.2020 14:38:54
-- Design Name: 
-- Module Name: mux9_16bit - Behavioral
-- Project Name: 
-- Target Devices: 
-- Tool Versions: 
-- Description: 
-- 
-- Dependencies: 
-- 
-- Revision:
-- Revision 0.01 - File Created
-- Additional Comments:
-- 
----------------------------------------------------------------------------------


library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

-- Uncomment the following library declaration if using
-- arithmetic functions with Signed or Unsigned values
--use IEEE.NUMERIC_STD.ALL;
library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity mux9_16bit is
    Port ( s : in  STD_LOGIC_VECTOR (3 downto 0);
           in1 : in  STD_LOGIC_VECTOR (15 downto 0);
           in2 : in  STD_LOGIC_VECTOR (15 downto 0);
           in3 : in  STD_LOGIC_VECTOR (15 downto 0);
           in4 : in  STD_LOGIC_VECTOR (15 downto 0);
           in5 : in  STD_LOGIC_VECTOR (15 downto 0);
           in6 : in  STD_LOGIC_VECTOR (15 downto 0);
           in7 : in  STD_LOGIC_VECTOR (15 downto 0);
           in8 : in  STD_LOGIC_VECTOR (15 downto 0);
           in9 : in  STD_LOGIC_VECTOR (15 downto 0);
           z : out  STD_LOGIC_VECTOR (15 downto 0));
end mux9_16bit;

architecture Behavioral of mux9_16bit is
begin
		z<= in1 after 5ns when s = "0000" else
		    in2 after 5ns when s = "0001" else
		    in3 after 5ns when s = "0010" else
		    in4 after 5ns when s = "0011" else
		    in5 after 5ns when s = "0100" else
		    in6 after 5ns when s = "0101" else
		    in7 after 5ns when s = "0110" else
		    in8 after 5ns when s = "0111" else
		    in8 after 5ns when s = "1000" else
		    "0000000000000000" after 5ns;
end Behavioral;

