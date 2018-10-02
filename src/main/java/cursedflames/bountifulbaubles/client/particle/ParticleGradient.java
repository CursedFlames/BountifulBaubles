package cursedflames.bountifulbaubles.client.particle;

import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

//TODO maybe move to CL
public class ParticleGradient extends Particle {
	float rDiff, gDiff, bDiff;
	float spinSpeed;

	public ParticleGradient(World world, double x, double y, double z, float r, float g, float b,
			float rT, float gT, float bT, int maxAge) {
		// TODO add small velocity randomization
		this(world, x, y, z, r, g, b, rT, gT, bT, maxAge, 0D, 0D, 0D);
	}

	public ParticleGradient(World world, double x, double y, double z, float r, float g, float b,
			float rT, float gT, float bT, int maxAge, double velX, double velY, double velZ) {
		super(world, x, y, z);
		motionX = velX;
		motionY = velY;
		motionZ = velZ;
		spinSpeed = 0.1F;
		this.particleRed = r;
		this.particleGreen = g;
		this.particleBlue = b;
		this.particleMaxAge = maxAge;

		rDiff = (rT-r)/maxAge;
		gDiff = (gT-g)/maxAge;
		bDiff = (bT-b)/maxAge;

		// No idea how this works
		this.setParticleTextureIndex((int) (4+Math.random()*4D));
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		particleRed += rDiff;
		particleGreen += gDiff;
		particleBlue += bDiff;
		this.particleAngle += spinSpeed;
	}

	// Thanks Elucent
	@Override
	public int getBrightnessForRender(float pTicks) {
		float f = 0.5F;
		f = MathHelper.clamp(f, 0.0F, 1.0F);
		int i = super.getBrightnessForRender(pTicks);
		int j = i&255;
		int k = i>>16&255;
		j = j+(int) (f*15.0F*16.0F);

		if (j>240) {
			j = 240;
		}

		return j|k<<16;
	}
}