----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 24.04.2020 14:18:47
-- Design Name: 
-- Module Name: mux8_1bit - Behavioral
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

-- Uncomment the following library declaration if instantiating
-- any Xilinx leaf cells in this code.
--library UNISIM;
--use UNISIM.VComponents.all;

entity mux8_1bit is
    Port ( s : in  STD_LOGIC_VECTOR(2 downto 0);
           in1 : in  STD_LOGIC;
           in2 : in  STD_LOGIC;
           in3 : in  STD_LOGIC;
           in4 : in  STD_LOGIC;
           in5 : in  STD_LOGIC;
           in6 : in  STD_LOGIC;    
           in7 : in  STD_LOGIC;
           in8 : in  STD_LOGIC;                             
           z : out  STD_LOGIC);
    end mux8_1bit;

architecture Behavioral of mux8_1bit is

begin
 process (s,in1,in2,in3,in4,in5,in6,in7,in8)
		begin
		case  s is
			when "000" => z <= in1;
			when "001" => z <= in2;
			when "010" => z <= in3;
			when "011" => z <= in4;
			when "100" => z <= in5;
			when "101" => z <= in6;
			when "110" => z <= in7;
			when "111" => z <= in8;									
			when others => z <= in1;
		end case;
	end process;


end Behavioral;
