----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 21.04.2020 16:48:50
-- Design Name: 
-- Module Name: zero_fill - Behavioral
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

entity zero_fill is
    Port ( 
        SB : in std_logic_vector(2 downto 0);
        z_out : out std_logic_vector(15 downto 0)
    );
end zero_fill;

architecture Behavioral of zero_fill is
    signal zeros : std_logic_vector(15 downto 0);
begin
    z_out(15 downto 3) <= "0000000000000";
    z_out(2 downto 0) <= SB;
end Behavioral;