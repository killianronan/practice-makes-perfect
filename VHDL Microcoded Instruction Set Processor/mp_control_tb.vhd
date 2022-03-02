----------------------------------------------------------------------------------
-- Company: 
-- Engineer: 
-- 
-- Create Date: 24.04.2020 15:04:18
-- Design Name: 
-- Module Name: mp_control_tb - Behavioral
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

entity mp_control_tb is
--  Port ( );
end mp_control_tb;

architecture Behavioral of mp_control_tb is
component mp_control is 
    Port ( 
           data_in : in  STD_LOGIC_VECTOR(15 downto 0);
           v, c, n, z, Clk,reset  : in STD_LOGIC;
           PC_out : out  STD_LOGIC_VECTOR(15 downto 0);
           FS : out STD_LOGIC_VECTOR(4 downto 0);
           DR,SA,SB,MS : out  STD_LOGIC_VECTOR(2 downto 0);
           TD,TA,TB,MB,RW,MD,MM,MW,PL,PI,IL,MC : out STD_LOGIC;
           NA : out std_logic_vector(7 downto 0)
     );
end component;
    signal Clk, reset, TD, TA, TB, MB, MD, RW, MM, MW,PL_sig, PI_sig, IL_sig, MC_sig : std_logic;
    signal v, c, n, z: std_logic := '0';
    signal data_in, PC : std_logic_vector(15 downto 0);
    signal NA_sig : std_logic_vector(7 downto 0);    
    signal FS : std_logic_vector(4 downto 0);
    signal DR, SA, SB, MS_sig : std_logic_vector(2 downto 0);
   
begin
    UTT: mp_control
        port map (
            MW => MW,
            RW => RW,
            MD => MD,
            FS => FS,
            MB => MB,
            TB => TB,
            TA => TA,
            TD => TD,
            MM => MM,
            data_in => data_in,
            clk => clk,
            reset => reset,
            PC_out => PC,            
            PL => PL_sig,
            PI => PI_sig,
            IL => IL_sig,
            MC => MC_sig,
            MS => MS_sig,
            NA => NA_sig,
            DR => DR,
            SA => SA,
            SB => SB,
            v => v,
            c => c,
            n => n,
            z => c
        );
process
    begin
    --initialize with reset
        clk <= '1';
        reset <= '1';
        wait for 40 ns;
        clk <= '0';
        reset <= '0';
        wait for 40ns;
    --Instruction 1
        data_in <= x"0000";
        
        clk <= '1';
        wait for 40ns;
        clk <= '0';
        wait for 40ns; 
        clk <= '1';
        wait for 40ns;
        clk <= '0';
        wait for 40ns;
        clk <= '1';
        wait for 40ns;
        clk <= '0';
        wait for 40ns;
        
        
        data_in <= x"0700";
        clk <= '1';
        wait for 40ns;
        clk <= '0';
        wait for 40ns;
        
        clk <= '1';
        wait for 40ns;
        clk <= '0';
        wait for 40ns;
        
        clk <= '1';
        wait for 40ns;
        clk <= '0';
        wait for 40ns;    
end process;

end Behavioral;
