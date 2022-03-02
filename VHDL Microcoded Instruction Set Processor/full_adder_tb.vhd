library IEEE;
use IEEE.STD_LOGIC_1164.ALL;

entity full_adder_tb is
end full_adder_tb;

architecture Behavioral of full_adder_tb is
    component full_adder is
        Port ( 
		    x : in STD_LOGIC;
			y : in STD_LOGIC;
			z : in STD_LOGIC;
			s : out STD_LOGIC;
			c : out STD_LOGIC
		);
    end component;     
    --input signals    
    signal x,y,z : std_logic := '0';    
    --output signals
    signal s, c : std_logic := '0';  
begin
    UUT: full_adder Port Map(
        x => x,
        y => y,
        z => z,
        s => s,
        c => c
    );
sim_proc :process
begin
       	x <= '0';
       	y <= '0';
       	z <= '0';
        wait for 2ns;
        
       	x <= '0';
       	y <= '0';
       	z <= '1';
        wait for 2ns;
        
       	x <= '0';
       	y <= '1';
       	z <= '0';
        wait for 2ns;
        
       	x <= '0';
       	y <= '1';
       	z <= '1';
        wait for 2ns;
        
       	x <= '1';
       	y <= '0';
       	z <= '0';
        wait for 2ns;
        
       	x <= '1';
       	y <= '0';
       	z <= '1';
        wait for 2ns;
        
       	x <= '1';
       	y <= '1';
       	z <= '0';
        wait for 2ns;
        
       	x <= '1';
       	y <= '1';
       	z <= '1';
        wait for 2ns;
     end process;
end Behavioral;