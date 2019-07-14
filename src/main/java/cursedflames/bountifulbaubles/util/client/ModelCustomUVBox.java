package cursedflames.bountifulbaubles.util.client;

import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.PositionTextureVertex;
import net.minecraft.client.model.TexturedQuad;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * A {@link ModelBox} that allows you to have non-1:1 texture coordinates and
 * position coordinates
 * 
 * @author CursedFlames
 *
 */
//TODO more versatile model stuff
public class ModelCustomUVBox extends ModelBox {
	/** An array of 6 TexturedQuads, one for each face of a cube */
	public TexturedQuad[] quadList2 = null;

	public ModelCustomUVBox(ModelRenderer renderer, int texU, int texV, float x, float y, float z,
			float dx, float dy, float dz, float scale) {
		this(renderer, texU, texV, x, y, z, dx, dy, dz, scale, renderer.mirror);
	}

	public ModelCustomUVBox(ModelRenderer renderer, int texU, int texV, float x, float y, float z,
			float dx, float dy, float dz, float scale, boolean mirror) {
		super(renderer, texU, texV, x, y, z, (int) dx, (int) dy, (int) dz, scale, mirror);

	}

	public ModelCustomUVBox(ModelRenderer renderer, int texU, int texV, float x, float y, float z,
			float dx, float dy, float dz, float scale, boolean mirror, int texDx, int texDy,
			int texDz) {
		super(renderer, texU, texV, x, y, z, (int) dx, (int) dy, (int) dz, scale, mirror);

		quadList2 = new TexturedQuad[6];

		float f = x+dx;
		float f1 = y+dy;
		float f2 = z+dz;
		x = x-scale;
		y = y-scale;
		z = z-scale;
		f = f+scale;
		f1 = f1+scale;
		f2 = f2+scale;

		if (mirror) {
			float f3 = f;
			f = x;
			x = f3;
		}

		//@formatter:off
		
		PositionTextureVertex positiontexturevertex7 = new PositionTextureVertex(x, y, z, 0.0F, 0.0F);
        PositionTextureVertex positiontexturevertex = new PositionTextureVertex(f, y, z, 0.0F, 8.0F);
        PositionTextureVertex positiontexturevertex1 = new PositionTextureVertex(f, f1, z, 8.0F, 8.0F);
        PositionTextureVertex positiontexturevertex2 = new PositionTextureVertex(x, f1, z, 8.0F, 0.0F);
        PositionTextureVertex positiontexturevertex3 = new PositionTextureVertex(x, y, f2, 0.0F, 0.0F);
        PositionTextureVertex positiontexturevertex4 = new PositionTextureVertex(f, y, f2, 0.0F, 8.0F);
        PositionTextureVertex positiontexturevertex5 = new PositionTextureVertex(f, f1, f2, 8.0F, 8.0F);
        PositionTextureVertex positiontexturevertex6 = new PositionTextureVertex(x, f1, f2, 8.0F, 0.0F);
		
		this.quadList2[0] = new TexturedQuad(new PositionTextureVertex[] {positiontexturevertex4, positiontexturevertex, positiontexturevertex1, positiontexturevertex5}, texU + texDz + texDx, texV + texDz, texU + texDz + texDx + texDz, texV + texDz + texDy, renderer.textureWidth, renderer.textureHeight);
		this.quadList2[1] = new TexturedQuad(new PositionTextureVertex[] {positiontexturevertex7, positiontexturevertex3, positiontexturevertex6, positiontexturevertex2}, texU, texV + texDz, texU + texDz, texV + texDz + texDy, renderer.textureWidth, renderer.textureHeight);
        this.quadList2[2] = new TexturedQuad(new PositionTextureVertex[] {positiontexturevertex4, positiontexturevertex3, positiontexturevertex7, positiontexturevertex}, texU + texDz, texV, texU + texDz + texDx, texV + texDz, renderer.textureWidth, renderer.textureHeight);
        this.quadList2[3] = new TexturedQuad(new PositionTextureVertex[] {positiontexturevertex1, positiontexturevertex2, positiontexturevertex6, positiontexturevertex5}, texU + texDz + texDx, texV + texDz, texU + texDz + texDx + texDx, texV, renderer.textureWidth, renderer.textureHeight);
        this.quadList2[4] = new TexturedQuad(new PositionTextureVertex[] {positiontexturevertex, positiontexturevertex7, positiontexturevertex2, positiontexturevertex1}, texU + texDz, texV + texDz, texU + texDz + texDx, texV + texDz + texDy, renderer.textureWidth, renderer.textureHeight);
        this.quadList2[5] = new TexturedQuad(new PositionTextureVertex[] {positiontexturevertex3, positiontexturevertex4, positiontexturevertex5, positiontexturevertex6}, texU + texDz + texDx + texDz, texV + texDz, texU + texDz + texDx + texDz + texDx, texV + texDz + texDy, renderer.textureWidth, renderer.textureHeight);
        
        //@formatter:on

		if (mirror) {
			for (TexturedQuad texturedquad : this.quadList2) {
				texturedquad.flipFace();
			}
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void render(BufferBuilder renderer, float scale) {
		if (quadList2==null) {
			super.render(renderer, scale);
			return;
		}
		for (TexturedQuad texturedquad : this.quadList2) {
			texturedquad.draw(renderer, scale);
		}
	}
}
