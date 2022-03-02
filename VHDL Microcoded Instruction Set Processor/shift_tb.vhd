library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
entity shift_tb is
end shift_tb;

architecture Behavioral of shift_tb is
    component shift is
        Port ( 
		    in0 : in std_logic;
			in1 : in std_logic;
			in2 : in std_logic;
			s : in std_logic_vector(1 downto 0);
			z : out std_logic
		);
    end component;
    --inputs    
    signal in0 : std_logic := '0';
    signal in1 : std_logic := '0';
    signal in2 : std_logic := '0';
    signal s : std_logic_vector(1 downto 0) := "00"; 
    --outputs 
    signal z : std_logic := '0';  
       
begin
    UUT: shift
    Port Map(
        in0 => in0,
        in1 => in1,
        in2 => in2,
        s => s,
        z => z
    );
sim_proc :process
begin
       	In0 <= '1';
       	In1 <= '0';
       	In2 <= '1';
       	s <= "00"; --select line 0
        wait for 2ns;
       	s <= "01"; --select line 1
        wait for 2ns;
       	s <= "10"; --select line 2
        wait for 2ns;
     end process;
end Behavioral;