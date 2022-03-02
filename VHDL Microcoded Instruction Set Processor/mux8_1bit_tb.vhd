----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 24.04.2020 14:22:20
-- Design Name: 
-- Module Name: mux8_1bit_tb - Behavioral
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

entity mux8_1bit_tb is
--  Port ( );
end mux8_1bit_tb;

architecture Behavioral of mux8_1bit_tb is
    COMPONENT mux8_1bit
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
     end component;
     
   --Inputs
   signal s : std_logic_vector(2 downto 0);
   signal in1 : std_logic := '0';
   signal in2 : std_logic := '0';
   signal in3 : std_logic := '0';
   signal in4 : std_logic := '0';
   signal in5 : std_logic := '0';
   signal in6 : std_logic := '0';
   signal in7 : std_logic := '0';
   signal in8 : std_logic := '0';         
 	--Outputs
   signal z : std_logic;
begin
    UUT : mux8_1bit Port Map(
        in1 => in1,
        in2 => in2,
        in3 => in3,
        in4 => in4,
        in5 => in5,
        in6 => in6,
        in7 => in7,
        in8 => in8,                        
        s => s,
        z => z
   );
sim_proc: process
   begin		
        in1 <= '1';
        s <= "000";
      wait for 5 ns;
        in1 <= '0';	
        in2 <= '1';
        s <= "001";
      wait for 5 ns;
        in2 <= '0';	
        in3 <= '1';
        s <= "010";
      wait for 5 ns;      
        in3 <= '0';	
        in4 <= '1';
        s <= "011";
      wait for 5 ns;          
        in4 <= '0';	
        in5 <= '1';
        s <= "100";
      wait for 5 ns;  
        in5 <= '0';	
        in6 <= '1';
        s <= "101";
      wait for 5 ns;                
        in6 <= '0';	
        in7 <= '1';
        s <= "110";
      wait for 5 ns;  
        in7 <= '0';	
        in8 <= '1';
        s <= "111";
      wait for 5 ns; 
        in8 <= '0';  
      wait for 5 ns; 
   end process;
end Behavioral;
