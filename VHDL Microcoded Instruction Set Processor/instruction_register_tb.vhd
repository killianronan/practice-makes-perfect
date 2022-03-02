----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 23.04.2020 18:36:38
-- Design Name: 
-- Module Name: instruction_register_tb - Behavioral
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

entity instruction_register_tb is
--  Port ( );
end instruction_register_tb;

architecture Behavioral of instruction_register_tb is
 component instruction_register is
  Port (
       instruction : in std_logic_vector(15 downto 0);
       IL : in std_logic;
       Clk : in std_logic;
       op_out : out std_logic_vector(6 downto 0);
       DR,SA,SB : out std_logic_vector(2 downto 0)
        );
end component;
    --Input    
    signal instruction : std_logic_vector(15 downto 0):= x"0000";
    signal IL : std_logic := '0';
    signal Clk : std_logic := '0';
    
    --Output
    signal op_out : std_logic_vector(6 downto 0):= "0000000";
    signal DR,SA,SB : std_logic_vector(2 downto 0):= "000";

begin
    UUT: instruction_register
    Port Map(
        instruction => instruction,
        Clk => Clk,
        IL => IL,
        op_out => op_out,
        DR => DR,
        SB => SB,
        SA => SA
    );

simulation_process :process
begin
        --Should produce empty instruction
        IL <= '1';
        instruction <= x"0000";
--        Clk <= '1';
        wait for 15ns;   
--        Clk <= '0';
--        wait for 15ns;    
        
        --IL not set, should do nothing
        IL <= '0';
        instruction <= x"1234";
--        Clk <= '1';
        wait for 15ns;   
--        CLK <= '0';
--        wait for 15ns;   
        
        --Should set instruction with all bits
        IL <= '1';
        instruction <= x"FFFF";
--        Clk <= '1';
        wait for 15ns;
--        Clk <= '0';
--        wait for 15ns;
  end process;

end Behavioral;
