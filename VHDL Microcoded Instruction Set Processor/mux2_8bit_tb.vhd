----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 24.04.2020 14:12:22
-- Design Name: 
-- Module Name: mux2_8bit_tb - Behavioral
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

entity mux2_8bit_tb is
--  Port ( );
end mux2_8bit_tb;

architecture Behavioral of mux2_8bit_tb is
    COMPONENT mux2_8bit
    PORT ( s : in  STD_LOGIC;
           in1 : in  STD_LOGIC_VECTOR (7 downto 0);
           in2 : in  STD_LOGIC_VECTOR (7 downto 0);
           z : out  STD_LOGIC_VECTOR (7 downto 0)
     );
    END COMPONENT;
   --Inputs
   signal s : std_logic :=  '0';
   signal in1 : std_logic_vector(7 downto 0) := (others => '0');
   signal in2 : std_logic_vector(7 downto 0) := (others => '0');
 	--Outputs
   signal z : std_logic_vector(7 downto 0);

begin
    UUT : mux2_8bit Port Map(
        In1 => In1,
        In2 => In2,
        s => s,
        Z => Z
   );
sim_proc: process
   begin		
        in1 <= "11111111";
		in2 <= "10000000";
      wait for 5 ns;	
        s <= '0';
      wait for 5 ns;	
		s <= '1';
      wait for 5 ns;
   end process;
end Behavioral;
